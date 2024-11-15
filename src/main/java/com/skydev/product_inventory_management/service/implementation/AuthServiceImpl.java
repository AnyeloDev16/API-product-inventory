package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.CredentialEntity;
import com.skydev.product_inventory_management.persistence.entity.RoleEntity;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.repository.ICredentialEntityRepository;
import com.skydev.product_inventory_management.persistence.repository.IRoleEntityRepository;
import com.skydev.product_inventory_management.persistence.repository.IUserEntityRepository;
import com.skydev.product_inventory_management.presentation.dto.request.LoginUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.request.RegisterUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.exceptions.InvalidPasswordException;
import com.skydev.product_inventory_management.service.exceptions.UserNotActiveException;
import com.skydev.product_inventory_management.service.interfaces.IAuthService;
import com.skydev.product_inventory_management.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserEntityRepository userRepository;
    private final IRoleEntityRepository roleRepository;
    private final ICredentialEntityRepository credentialRepository;
    private final MessageUtil messageUtil;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional(readOnly = true)
    public ResponseUserAuthDTO login(LoginUserAuthDTO loginUserAuthDTO) {

        String username = loginUserAuthDTO.getUsername();
        String password = loginUserAuthDTO.getPassword();

        Authentication authentication = authenticate(username, password);

        CredentialEntity credential = credentialRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(messageUtil.CREDENTIALS_USERNAME_NOT_FOUND));

        validateUserActive(credential);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseUserDTO responseUserDTO = modelMapper.map(credential.getUser(), ResponseUserDTO.class);
        ResponseAuthDTO responseAuthDTO = ResponseAuthDTO.builder()
                .username(username)
                .message(messageUtil.LOGIN_SUCCESSFUL)
                .build();

        return ResponseUserAuthDTO.builder()
                .user(responseUserDTO)
                .auth(responseAuthDTO)
                .build();

    }

    @Transactional
    public ResponseUserAuthDTO register(RegisterUserAuthDTO registerUserAuthDTO) {

        RoleEntity role = modelMapper.map(registerUserAuthDTO, RoleEntity.class);

        role = roleRepository.findByRoleName(role.getRoleName())
                .orElseThrow(() -> new InvalidInputException(messageUtil.ROLE_NOT_FOUND));

        UserEntity newUser = modelMapper.map(registerUserAuthDTO, UserEntity.class);

        newUser.setRole(role);

        newUser.setActive(true);

        newUser = userRepository.save(newUser);

        CredentialEntity credential = modelMapper.map(registerUserAuthDTO, CredentialEntity.class);

        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        credential.setUser(newUser);
        credentialRepository.save(credential);

        ResponseUserDTO responseUserDTO = modelMapper.map(newUser, ResponseUserDTO.class);
        ResponseAuthDTO responseAuthDTO = ResponseAuthDTO.builder()
                .username(credential.getUsername())
                .message(messageUtil.REGISTER_SUCCESSFUL)
                .build();

        return ResponseUserAuthDTO.builder().auth(responseAuthDTO).user(responseUserDTO).build();

    }

    @Transactional(readOnly = true)
    public Authentication authenticate(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException(messageUtil.CREDENTIALS_INVALID_PASSWORD);
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }

    public void validateUserActive(CredentialEntity credential) {
        if(Boolean.FALSE.equals(credential.getUser().getActive())) {
            throw new UserNotActiveException(messageUtil.USER_NOT_ACTIVE);
        }
    }

}

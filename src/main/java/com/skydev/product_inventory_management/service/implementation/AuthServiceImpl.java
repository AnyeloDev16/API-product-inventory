package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.CredentialEntity;
import com.skydev.product_inventory_management.persistence.entity.RoleEntity;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.repository.ICredentialEntityRepository;
import com.skydev.product_inventory_management.persistence.repository.IRoleEntityRepository;
import com.skydev.product_inventory_management.persistence.repository.IUserEntityRepository;
import com.skydev.product_inventory_management.presentation.dto.request.user.LoginUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.request.user.RegisterUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.exceptions.InvalidPasswordException;
import com.skydev.product_inventory_management.service.exceptions.UserNotActiveException;
import com.skydev.product_inventory_management.service.interfaces.IAuthService;
import com.skydev.product_inventory_management.util.JwtUtils;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserEntityRepository userRepository;
    private final IRoleEntityRepository roleRepository;
    private final ICredentialEntityRepository credentialRepository;
    private final MessageUtils messageUtils;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    @Transactional(readOnly = true)
    public ResponseUserAuthDTO login(LoginUserAuthDTO loginUserAuthDTO) {

        String username = loginUserAuthDTO.getUsername();
        String password = loginUserAuthDTO.getPassword();

        Authentication authentication = authenticate(username, password);

        CredentialEntity credential = credentialRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(messageUtils.CREDENTIALS_USERNAME_NOT_FOUND));

        UserEntity user = credential.getUser();

        validateUserActive(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication, user.getUserId());

        ResponseUserDTO responseUserDTO = modelMapper.map(user, ResponseUserDTO.class);

        return ResponseUserAuthDTO.builder()
                .auth(messageUtils.LOGIN_SUCCESSFUL)
                .user(responseUserDTO)
                .jwtToken(accessToken)
                .build();

    }

    @Transactional
    public ResponseUserAuthDTO register(RegisterUserAuthDTO registerUserAuthDTO) {

        RoleEntity role = modelMapper.map(registerUserAuthDTO, RoleEntity.class);

        role = roleRepository.findByRoleName(role.getRoleName())
                .orElseThrow(() -> new InvalidInputException(messageUtils.ROLE_NOT_FOUND));

        UserEntity newUser = modelMapper.map(registerUserAuthDTO, UserEntity.class);

        newUser.setRole(role);

        newUser.setActive(true);

        newUser = userRepository.save(newUser);

        CredentialEntity credential = modelMapper.map(registerUserAuthDTO, CredentialEntity.class);

        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        credential.setUser(newUser);
        credential = credentialRepository.save(credential);

        Authentication authentication = new UsernamePasswordAuthenticationToken(credential.getUsername(), null, getSimpleGrantedAuthorities(role));

        String accessToken = jwtUtils.createToken(authentication, newUser.getUserId());

        ResponseUserDTO responseUserDTO = modelMapper.map(newUser, ResponseUserDTO.class);

        return ResponseUserAuthDTO.builder()
                                    .auth(messageUtils.REGISTER_SUCCESSFUL)
                                    .user(responseUserDTO)
                                    .jwtToken(accessToken)
                                    .build();

    }

    private List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(RoleEntity role) {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName())));
        return authorityList;
    }

    @Transactional(readOnly = true)
    public Authentication authenticate(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException(messageUtils.CREDENTIALS_INVALID_PASSWORD);
        }

        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

    }

    public void validateUserActive(UserEntity userEntity) {
        if(Boolean.FALSE.equals(userEntity.getActive())) {
            throw new UserNotActiveException(messageUtils.USER_NOT_ACTIVE);
        }
    }

}

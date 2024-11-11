package com.skydev.product_inventory_management.service.implementation;

import java.util.List;

import com.skydev.product_inventory_management.persistence.entity.RoleEntity;
import com.skydev.product_inventory_management.persistence.repository.IRoleEntityRepository;
import com.skydev.product_inventory_management.presentation.dto.request.RegisterUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserAuthDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.util.UserEntityHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skydev.product_inventory_management.persistence.entity.CredentialEntity;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.repository.ICredentialEntityRepository;
import com.skydev.product_inventory_management.persistence.repository.IUserEntityRepository;
import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseUser;
import com.skydev.product_inventory_management.presentation.dto.request.UpdatePasswordUserDTO;
import com.skydev.product_inventory_management.presentation.dto.request.UpdateUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.IUserEntityService;

@Service
public class UserEntityServiceImpl implements IUserEntityService{

    private final ModelMapper modelMapper;
    private final IUserEntityRepository userRepository;
    private final ICredentialEntityRepository credentialRepository;
    private final IRoleEntityRepository roleRepository;

    @Value("${message.register.successful}")
    private String registerSuccessMessage;

    @Value("${error.message.role_not_found}")
    private String roleNotFoundMessage;

    @Value("${error.message.user_id_not_found}")
    private String userIdNotFoundMessage;

    public UserEntityServiceImpl(ModelMapper modelMapper, IUserEntityRepository userRepository, ICredentialEntityRepository credentialRepository, IRoleEntityRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public ResponseUserAuthDTO saveUser(RegisterUserAuthDTO registerUserAuthDTO) {
        RoleEntity role = modelMapper.map(registerUserAuthDTO, RoleEntity.class);

        role = roleRepository.findByRoleName(role.getRoleName())
                .orElseThrow(() -> new InvalidInputException(List.of(roleNotFoundMessage)));

        UserEntity newUser = modelMapper.map(registerUserAuthDTO, UserEntity.class);

        newUser.setRole(role);

        newUser.setActive(true);

        newUser = userRepository.save(newUser);

        CredentialEntity credential = modelMapper.map(registerUserAuthDTO, CredentialEntity.class);
        // Then Encrypt
        credential.setPassword(credential.getPassword());
        credential.setUser(newUser);
        credentialRepository.save(credential);

        ResponseUserDTO responseUserDTO = modelMapper.map(newUser, ResponseUserDTO.class);
        ResponseAuthDTO responseAuthDTO = ResponseAuthDTO.builder()
                                                            .username(credential.getUsername())
                                                            .message(registerSuccessMessage)
                                                            .build();

        return ResponseUserAuthDTO.builder().auth(responseAuthDTO).user(responseUserDTO).build();
    }

    @Override
    @Transactional
    public ResponseUserDTO updateUser(Long idUser, UpdateUserDTO updateUserDTO) {
        UserEntity findUser = userRepository.findById(idUser)
                                            .orElseThrow(() -> 
                                                     new EntityNotFoundException(List.of(userIdNotFoundMessage)));

        UserEntityHelper.updateUser(findUser, updateUserDTO);

        findUser = userRepository.save(findUser);

        return modelMapper.map(findUser, ResponseUserDTO.class);

    }

    @Override
    @Transactional
    public void updatePassword(Long idUser, UpdatePasswordUserDTO updatePasswordUserDTO) {

        CredentialEntity findCredential = credentialRepository.findByUser_userId(idUser)
                                                .orElseThrow(() -> 
                                                    new EntityNotFoundException(List.of(userIdNotFoundMessage)));
        // Then Encrypt
        findCredential.setPassword(updatePasswordUserDTO.getNewPassword());
        
        credentialRepository.save(findCredential);

    }

    @Override
    @Transactional
    public void updateActive(Long idUser) {

        UserEntity findUser = userRepository.findById(idUser)
                .orElseThrow(() ->
                        new EntityNotFoundException(List.of(userIdNotFoundMessage)));

        findUser.setActive(!findUser.getActive());

        userRepository.save(findUser);

    }

    @Override
    public <T extends IResponseUser> T findUserById(Long idUser, Class<T> dtoClass) {
        UserEntity findUser = userRepository.findById(idUser)
                                            .orElseThrow(() -> 
                                                     new EntityNotFoundException(List.of(userIdNotFoundMessage)));

        return modelMapper.map(findUser, dtoClass);
                                            
    }

    @Override
    public <T extends IResponseUser> List<T> findUserAll(Class<T> dtoClass) {

        return userRepository.findAll().stream()
                .map(userE -> modelMapper.map(userE, dtoClass))
                .toList();

    }

}

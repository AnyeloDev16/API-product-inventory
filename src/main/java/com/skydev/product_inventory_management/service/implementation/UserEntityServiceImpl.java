package com.skydev.product_inventory_management.service.implementation;

import java.util.List;

import com.skydev.product_inventory_management.util.MessageUtils;
import com.skydev.product_inventory_management.util.EntityHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skydev.product_inventory_management.persistence.entity.CredentialEntity;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.repository.ICredentialEntityRepository;
import com.skydev.product_inventory_management.persistence.repository.IUserEntityRepository;
import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseUser;
import com.skydev.product_inventory_management.presentation.dto.request.user.UpdatePasswordUserDTO;
import com.skydev.product_inventory_management.presentation.dto.request.user.UpdateUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.IUserEntityService;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements IUserEntityService{

    private final ModelMapper modelMapper;
    private final IUserEntityRepository userRepository;
    private final ICredentialEntityRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtils messageUtils;

    @Override
    @Transactional
    public ResponseUserDTO updateUser(Long idUser, UpdateUserDTO updateUserDTO) {

        UserEntity findUser = userRepository.findById(idUser)
                                            .orElseThrow(() -> 
                                                     new EntityNotFoundException(messageUtils.USER_ID_NOT_FOUND));

        EntityHelper.updateUser(findUser, updateUserDTO);

        findUser = userRepository.save(findUser);

        return modelMapper.map(findUser, ResponseUserDTO.class);

    }

    @Override
    @Transactional
    public void updatePassword(Long idUser, UpdatePasswordUserDTO updatePasswordUserDTO) {

        CredentialEntity findCredential = credentialRepository.findByUser_userId(idUser)
                                                .orElseThrow(() -> 
                                                    new EntityNotFoundException(messageUtils.USER_ID_NOT_FOUND));

        findCredential.setPassword(passwordEncoder.encode(updatePasswordUserDTO.getNewPassword()));
        
        credentialRepository.save(findCredential);

    }

    @Override
    @Transactional
    public void updateActive(Long idUser) {

        UserEntity findUser = userRepository.findById(idUser)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageUtils.USER_ID_NOT_FOUND));

        findUser.setActive(!findUser.getActive());

        userRepository.save(findUser);

    }

    @Override
    @Transactional(readOnly = true)
    public <T extends IResponseUser> T findUserById(Long idUser, Class<T> dtoClass) {

        UserEntity findUser = userRepository.findById(idUser)
                                            .orElseThrow(() -> 
                                                     new EntityNotFoundException(messageUtils.USER_ID_NOT_FOUND));

        return modelMapper.map(findUser, dtoClass);
                                            
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends IResponseUser> List<T> findUserAll(Class<T> dtoClass) {

        return userRepository.findAll().stream()
                .map(userE -> modelMapper.map(userE, dtoClass))
                .toList();

    }

}

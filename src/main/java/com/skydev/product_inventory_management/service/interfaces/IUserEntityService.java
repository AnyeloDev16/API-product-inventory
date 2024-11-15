package com.skydev.product_inventory_management.service.interfaces;

import java.util.List;

import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseUser;
import com.skydev.product_inventory_management.presentation.dto.request.UpdatePasswordUserDTO;
import com.skydev.product_inventory_management.presentation.dto.request.UpdateUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserDTO;

public interface IUserEntityService {

    ResponseUserDTO updateUser(Long idUser, UpdateUserDTO updateUserDTO);
    void updatePassword(Long idUser, UpdatePasswordUserDTO updatePasswordUserDTO);
    void updateActive(Long idUser);
    <T extends IResponseUser> T findUserById(Long idUser, Class<T> dtoClass);
    <T extends IResponseUser> List<T> findUserAll(Class<T> dtoClass);

}

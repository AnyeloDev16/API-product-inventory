package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.request.LoginUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.request.RegisterUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserAuthDTO;

public interface IAuthService {

    ResponseUserAuthDTO login(LoginUserAuthDTO loginAuth);
    ResponseUserAuthDTO register(RegisterUserAuthDTO registerUserAuthDTO);

}

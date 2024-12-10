package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.request.user.LoginUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.request.user.RegisterUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserAuthDTO;

public interface IAuthService {

    ResponseUserAuthDTO login(LoginUserAuthDTO loginAuth);
    ResponseUserAuthDTO register(RegisterUserAuthDTO registerUserAuthDTO);

}

package com.skydev.product_inventory_management.presentation.controller;

import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserAuthDTO;
import com.skydev.product_inventory_management.service.interfaces.IAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.skydev.product_inventory_management.presentation.dto.request.LoginUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.request.RegisterUserAuthDTO;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final IAuthService authService;

    @PostMapping("/log-in")
    public ResponseEntity<ResponseUserAuthDTO> login(@Valid @RequestBody LoginUserAuthDTO loginAuth) {

        ResponseUserAuthDTO response = authService.login(loginAuth);

        return ResponseEntity
                        .status(HttpStatus.OK)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getJwtToken())
                        .body(response);

    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseUserAuthDTO> register(@Valid @RequestBody RegisterUserAuthDTO registerUserDTO){

        ResponseUserAuthDTO response = authService.register(registerUserDTO);

        return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getJwtToken())
                        .body(response);
    }

}

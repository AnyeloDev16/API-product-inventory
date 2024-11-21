package com.skydev.product_inventory_management.presentation.controller;

import java.util.List;

import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseUser;
import com.skydev.product_inventory_management.presentation.dto.request.user.UpdatePasswordUserDTO;
import com.skydev.product_inventory_management.presentation.dto.request.user.UpdateUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserAdminDTO;
import com.skydev.product_inventory_management.service.interfaces.IUserEntityService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserEntityService userService;
    private final MessageUtils messageUtils;

    @PutMapping("/{idUser}")
    public ResponseEntity<ResponseUserDTO> updateUser(@PathVariable Long idUser, @Valid @RequestBody UpdateUserDTO updateUserDTO) {

        if(idUser <= 0){
            throw new InvalidInputException(messageUtils.USER_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(idUser, updateUserDTO));
    }

    @PutMapping("/{idUser}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long idUser, @Valid @RequestBody UpdatePasswordUserDTO updatePasswordUserDTO) {

        if(idUser <= 0){
            throw new InvalidInputException(messageUtils.USER_ID_INVALID);
        }

        userService.updatePassword(idUser, updatePasswordUserDTO);

        return ResponseEntity
                        .status(HttpStatus.OK)
                        .build();

    }

    @PutMapping("/{idUser}/active")
    public ResponseEntity<Void> updateActive(@PathVariable Long idUser) {

        if(idUser <= 0){
            throw new InvalidInputException(messageUtils.USER_ID_INVALID);
        }

        userService.updateActive(idUser);

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<IResponseUser> findUser(@PathVariable Long idUser){

        if(idUser <= 0){
            throw new InvalidInputException(messageUtils.USER_ID_INVALID);
        }

        return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(userService.findUserById(idUser, ResponseUserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserAdminDTO>> findAllUser(){
        return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(userService.findUserAll(ResponseUserAdminDTO.class));
    }
    
}

package com.skydev.product_inventory_management.presentation.controller;

import java.util.List;

import com.skydev.product_inventory_management.presentation.dto.request.RegisterUserAuthDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserAuthDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseUser;
import com.skydev.product_inventory_management.presentation.dto.request.UpdatePasswordUserDTO;
import com.skydev.product_inventory_management.presentation.dto.request.UpdateUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.ResponseUserAdminDTO;
import com.skydev.product_inventory_management.service.interfaces.IUserEntityService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserEntityService userService;

    public UserController(IUserEntityService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseUserAuthDTO> saveUser(@Valid @RequestBody RegisterUserAuthDTO registerUserAuthDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.saveUser(registerUserAuthDTO));
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<ResponseUserDTO> updateUser(@Positive(message = "{error.message.user_id_invalid}") @PathVariable Long idUser, @Valid @RequestBody UpdateUserDTO updateUserDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(idUser, updateUserDTO));
    }

    @PutMapping("/{idUser}/password")
    public ResponseEntity<Void> updatePassword(@Positive(message = "{error.message.user_id_invalid}") @PathVariable Long idUser, @Valid @RequestBody UpdatePasswordUserDTO updatePasswordUserDTO) {

        userService.updatePassword(idUser, updatePasswordUserDTO);

        return ResponseEntity
                        .status(HttpStatus.OK)
                        .build();

    }

    @PutMapping("/{idUser}/active")
    public ResponseEntity<Void> updateActive(@Positive(message = "{error.message.user_id_invalid}") @PathVariable Long idUser) {

        userService.updateActive(idUser);

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<IResponseUser> findUser(@Positive(message = "{error.message.user_id_invalid}") @PathVariable Long idUser){
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

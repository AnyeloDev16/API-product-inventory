package com.skydev.product_inventory_management.presentation.dto.response;

import java.time.LocalDate;

import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDTO implements IResponseUser{

    private Long userId;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String gender;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String role;
    private Boolean active;

}

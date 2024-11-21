package com.skydev.product_inventory_management.presentation.dto.request.user;

import java.time.LocalDate;

import com.skydev.product_inventory_management.persistence.entity.enums.Gender;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniqueEmail;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniquePhone;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniqueUsername;
import com.skydev.product_inventory_management.presentation.validation.annotations.ValidEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
public class RegisterUserAuthDTO {

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name length is min 3 and max 100")
    private String name;

    @NotNull(message = "First last name is required")
    @Size(min = 3, max = 100, message = "First last name length is min 3 and max 100")
    private String firstLastName;

    @NotNull(message = "Second last name is required")
    @Size(min = 3, max = 100, message = "Second last name length is min 3 and max 100")
    private String secondLastName;

    @NotNull(message = "Gender is required")
    @ValidEnum(enumClass = Gender.class, message = "Gender must be valid")
    private String gender;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Email is required")
    @Email(message = "Email format not valid")
    @UniqueEmail(message = "Email already exists")
    private String email;

    @Pattern(regexp = "^\\+[\\d]{1,3} [\\d]{7,14}$", message = "Phone format not valid")
    @UniquePhone(message = "Phone already exists")
    private String phone;

    @NotNull(message = "Role is required")
    private String role;

    @NotNull(message = "Username is required")
    @Size(min = 8, max = 25, message = "Username length is min 8 and max 25")
    @UniqueUsername(message = "Username already exists")
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 25, message = "Password length is min 8 and max 25")
    private String password;

}

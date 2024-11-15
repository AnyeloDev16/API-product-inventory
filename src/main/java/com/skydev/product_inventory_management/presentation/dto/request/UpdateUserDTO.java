package com.skydev.product_inventory_management.presentation.dto.request;

import java.time.LocalDate;

import com.skydev.product_inventory_management.persistence.entity.enums.Gender;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniquePhone;
import com.skydev.product_inventory_management.presentation.validation.annotations.ValidEnum;

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
public class UpdateUserDTO {

    @Size(min = 3, max = 100, message = "Name length is min 3 and max 100")
    private String name;

    @Size(min = 3, max = 100, message = "First last name length is min 3 and max 100")
    private String firstLastName;

    @Size(min = 3, max = 100, message = "Second last name length is min 3 and max 100")
    private String secondLastName;

    @ValidEnum(enumClass = Gender.class, message = "Gender must be valid")
    private String gender;

    @Past(message = "Birth date isn't past")
    private LocalDate birthDate;

    @Pattern(regexp = "^\\+[\\d]{1,3} [\\d]{7,14}$", message = "Phone format not valid")
    @UniquePhone(message = "Phone already exists")
    private String phone;

}

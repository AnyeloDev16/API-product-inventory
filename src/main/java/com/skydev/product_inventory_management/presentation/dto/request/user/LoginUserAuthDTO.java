package com.skydev.product_inventory_management.presentation.dto.request.user;

import jakarta.validation.constraints.NotNull;
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
public class LoginUserAuthDTO {

    @NotNull(message = "Username is required")
    @Size(min = 8, max = 25, message = "Username length is min 8 and max 25")
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 25, message = "Password length is min 8 and max 25")
    private String password;

}

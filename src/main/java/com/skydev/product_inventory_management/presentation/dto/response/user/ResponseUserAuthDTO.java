package com.skydev.product_inventory_management.presentation.dto.response.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ResponseUserAuthDTO {

    private String auth;
    private ResponseUserDTO user;

    @JsonIgnore
    private String jwtToken;

}

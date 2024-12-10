package com.skydev.product_inventory_management.presentation.dto.response.address;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAddressDTO {

    private String addressLine;
    private String zipCode;
    private String street;
    private String district;
    private String department;
    private String country;

}

package com.skydev.product_inventory_management.presentation.dto.request.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAddressDTO {

    @NotNull(message = "Address line is required")
    @Size(message = "Address line length is min 3 and max 255", min = 3, max = 255)
    private String addressLine;

    @NotNull(message = "Zip code is required")
    @Size(min = 3, max = 20, message = "Zip code length is min 3 and max 20")
    private String zipCode;

    @NotNull(message = "Street is required")
    @Size(min = 3, max = 100, message = "Street length is min 3 and max 100")
    private String street;

    @NotNull(message = "District is required")
    @Size(min = 3, max = 100, message = "District length is min 3 and max 100")
    private String district;

    @NotNull(message = "Department is required")
    @Size(min = 3, max = 100, message = "Department length is min 3 and max 100")
    private String department;

    @NotNull(message = "Country is required")
    @Size(min = 3, max = 100, message = "Country length is min 3 and max 100")
    private String country;

}

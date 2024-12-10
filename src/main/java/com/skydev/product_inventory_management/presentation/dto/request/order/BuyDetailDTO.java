package com.skydev.product_inventory_management.presentation.dto.request.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyDetailDTO {

    @NotNull(message = "Address is required")
    @Positive(message = "Address is invalid")
    private Long addressId;

    @NotNull(message = "Payment Method is required")
    @Positive(message = "Payment Method is invalid")
    private Long paymentMethodId;

}

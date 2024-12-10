package com.skydev.product_inventory_management.presentation.dto.request.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCartItemDTO {

    @NotNull(message = "Product is required")
    @Positive(message = "Product invalid")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity invalid")
    private Integer quantity;

}

package com.skydev.product_inventory_management.presentation.dto.request.order;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrderDetailDTO {

    @NotNull(message = "Product is required")
    @Positive(message = "Product invalid")
    private Long productId;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0.01")
    @DecimalMax(value = "99999999.99", message = "Unit price must be less than 99999999.99")
    private BigDecimal unitPrice;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity invalid")
    private Integer quantity;

}

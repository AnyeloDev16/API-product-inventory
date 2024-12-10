package com.skydev.product_inventory_management.presentation.dto.request.product;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDTO {

    @Positive(message = "Category id must be positive")
    private Long categoryId;

    @Size(min = 3, max = 100, message = "Product name length must be min 3 and max 100")
    private String productName;

    private String description;

    @DecimalMin(value = "0.01", message = "Purchase price must be greater than 0.01")
    @DecimalMax(value = "99999999.99", message = "Purchase price must be less than 99999999.99")
    private BigDecimal purchasePrice;

    @DecimalMin(value = "0.01", message = "Purchase price must be greater than 0.01")
    @DecimalMax(value = "99999999.99", message = "Purchase price must be less than 99999999.99")
    private BigDecimal salePrice;

    private Integer stock;

    private String productPath;

}

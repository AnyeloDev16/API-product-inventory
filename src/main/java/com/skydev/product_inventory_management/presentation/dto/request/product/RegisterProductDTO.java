package com.skydev.product_inventory_management.presentation.dto.request.product;

import com.skydev.product_inventory_management.presentation.validation.annotations.UniqueProductName;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterProductDTO {

    @NotNull(message = "Category is required")
    @Positive(message = "Category id must be positive")
    private Long categoryId;

    @NotNull(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name length must be min 3 and max 100")
    @UniqueProductName(message = "Product name already exists")
    private String productName;

    private String description;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0.01", message = "Purchase price must be greater than 0.01")
    @DecimalMax(value = "99999999.99", message = "Purchase price must be less than 99999999.99")
    private BigDecimal purchasePrice;

    @NotNull(message = "Sale price is required")
    @DecimalMin(value = "0.01", message = "Purchase price must be greater than 0.01")
    @DecimalMax(value = "99999999.99", message = "Purchase price must be less than 99999999.99")
    private BigDecimal salePrice;

    private Integer stock;

    @NotNull(message = "Image path is required")
    private String imagePath;

}

package com.skydev.product_inventory_management.presentation.dto.response.product;

import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseProduct;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductUserDTO implements IResponseProduct {

    private Long productId;
    private String categoryName;
    private String productName;
    private String description;
    private BigDecimal salePrice;
    private String imagePath;

}

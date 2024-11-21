package com.skydev.product_inventory_management.presentation.dto.response.category;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCategoryDTO {

    private Long categoryId;
    private String categoryName;

}

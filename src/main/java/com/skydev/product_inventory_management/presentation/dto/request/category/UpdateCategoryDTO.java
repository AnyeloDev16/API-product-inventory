package com.skydev.product_inventory_management.presentation.dto.request.category;

import com.skydev.product_inventory_management.presentation.validation.annotations.UniqueCategoryName;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDTO {

    @Size(min = 3, max = 100, message = "Category name length is min 3 and max 100")
    @UniqueCategoryName(message = "Category name already exists")
    private String categoryName;

}

package com.skydev.product_inventory_management.presentation.dto.request.order;

import com.skydev.product_inventory_management.persistence.entity.enums.OrderStatus;
import com.skydev.product_inventory_management.presentation.validation.annotations.ValidEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDTO {

    @NotNull(message = "New Order status is required")
    @ValidEnum(enumClass = OrderStatus.class, message = "Order status must be valid")
    private String orderStatus;

}

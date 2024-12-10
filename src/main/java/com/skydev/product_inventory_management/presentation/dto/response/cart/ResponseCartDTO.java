package com.skydev.product_inventory_management.presentation.dto.response.cart;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCartDTO {

    private Long cartId;
    private Long userId;

}

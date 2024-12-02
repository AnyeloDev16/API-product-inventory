package com.skydev.product_inventory_management.presentation.dto.response.order;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderBuyDTO {

    private String message;
    private Long orderID;
    private String orderStatus;

}

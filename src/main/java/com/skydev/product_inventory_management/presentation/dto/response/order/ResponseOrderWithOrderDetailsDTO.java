package com.skydev.product_inventory_management.presentation.dto.response.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderWithOrderDetailsDTO {

    private ResponseOrderDTO orderDetails;
    private List<ResponseOrderDetailDTO> listProduct;

}

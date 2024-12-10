package com.skydev.product_inventory_management.presentation.dto.response.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDTO {

    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private String paymentProvider;
    private String paymentMethod;
    private String orderStatus;

}

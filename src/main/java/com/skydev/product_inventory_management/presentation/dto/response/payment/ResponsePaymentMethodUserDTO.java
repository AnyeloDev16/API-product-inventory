package com.skydev.product_inventory_management.presentation.dto.response.payment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePaymentMethodUserDTO {

    private String paymentProvider;
    private String paymentType;
    private Boolean active;

}

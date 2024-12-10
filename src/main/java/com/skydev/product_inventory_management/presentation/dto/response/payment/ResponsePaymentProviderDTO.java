package com.skydev.product_inventory_management.presentation.dto.response.payment;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePaymentProviderDTO {

    private String providerName;
    private List<ResponsePaymentMethodDTO> listPaymentMethod;

}

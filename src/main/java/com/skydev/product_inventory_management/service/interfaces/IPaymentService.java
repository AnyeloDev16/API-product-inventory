package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.response.payment.ResponsePaymentMethodUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.payment.ResponsePaymentProviderDTO;

import java.util.List;

public interface IPaymentService {

    ResponsePaymentProviderDTO getPaymentProviderById(Long idPaymentProvider);
    List<ResponsePaymentProviderDTO> getAllPaymentProviders();
    ResponsePaymentMethodUserDTO getPaymentMethodById(Long idPaymentMethod);

}

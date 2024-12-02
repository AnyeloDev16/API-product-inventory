package com.skydev.product_inventory_management.presentation.controller;

import com.skydev.product_inventory_management.presentation.dto.response.payment.ResponsePaymentMethodUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.payment.ResponsePaymentProviderDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.interfaces.IPaymentService;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;
    private final MessageUtils messageUtils;

    @GetMapping("/provider/{idPaymentProvider}")
    public ResponseEntity<ResponsePaymentProviderDTO> getPaymentProviderById(@PathVariable Long idPaymentProvider) {

        if(idPaymentProvider <= 0){
            throw new InvalidInputException(messageUtils.PAYMENT_PROVIDER_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.getPaymentProviderById(idPaymentProvider));

    }

    @GetMapping("/provider")
    public ResponseEntity<List<ResponsePaymentProviderDTO>> getAllPaymentProviders(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.getAllPaymentProviders());

    }

    @GetMapping("/method/{idPaymentMethod}")
    public ResponseEntity<ResponsePaymentMethodUserDTO> getPaymentMethodById(@PathVariable Long idPaymentMethod) {

        if(idPaymentMethod <= 0){
            throw new InvalidInputException(messageUtils.PAYMENT_METHOD_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.getPaymentMethodById(idPaymentMethod));

    }

}

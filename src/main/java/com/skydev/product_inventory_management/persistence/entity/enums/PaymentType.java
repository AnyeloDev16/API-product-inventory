package com.skydev.product_inventory_management.persistence.entity.enums;

import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import lombok.Getter;

@Getter
public enum PaymentType {

    CREDIT_CARD("Credit card"),
    DEBIT_CARD("Debit card"),
    BANK_TRANSFER("Bank transfer"),
    WALLET("Wallet"),
    CASH("Cash");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    public static PaymentType convertToPaymentType(String paymentType){
        try{
            return PaymentType.valueOf(paymentType.toUpperCase());
        } catch (IllegalArgumentException iae){
            throw new InvalidInputException("Payment type is invalid");
        }
    }

}

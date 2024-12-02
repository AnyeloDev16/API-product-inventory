package com.skydev.product_inventory_management.persistence.entity.enums;

import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import lombok.Getter;

@Getter
public enum OrderStatus {

    PAID("Paid"),
    ON_THE_WAY("On the way"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static OrderStatus convertToOrderStatus(String orderStatus){
        try{
            return OrderStatus.valueOf(orderStatus.toUpperCase());
        } catch (IllegalArgumentException iae){
            throw new InvalidInputException("Order status is invalid");
        }
    }

}

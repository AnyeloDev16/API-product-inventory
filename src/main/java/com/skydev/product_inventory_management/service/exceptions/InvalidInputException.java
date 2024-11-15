package com.skydev.product_inventory_management.service.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message) {
        super(message);
    }

}

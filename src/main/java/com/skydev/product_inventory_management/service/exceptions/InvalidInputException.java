package com.skydev.product_inventory_management.service.exceptions;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidInputException extends RuntimeException{

    private final List<String> listError;

    public InvalidInputException(List<String> listError) {
        this.listError = listError;
    }

}

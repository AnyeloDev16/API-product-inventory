package com.skydev.product_inventory_management.service.exceptions;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException{

    private final List<String> listError;

    public EntityNotFoundException(List<String> listError) {
        this.listError = listError;
    }

}

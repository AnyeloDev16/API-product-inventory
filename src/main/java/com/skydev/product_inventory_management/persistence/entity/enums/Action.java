package com.skydev.product_inventory_management.persistence.entity.enums;

import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import lombok.Getter;

@Getter
public enum Action {
    CREATE("Create"),
    UPDATE("Update"),
    ACTIVE("Active"),
    INACTIVE("Inactive"),;

    private final String value;

    Action(String value) {
        this.value = value;
    }

    public static Action convertToAction(String action){
        try{
            return Action.valueOf(action.toUpperCase());
        } catch (IllegalArgumentException iae){
            throw new InvalidInputException("Action is invalid");
        }
    }

}

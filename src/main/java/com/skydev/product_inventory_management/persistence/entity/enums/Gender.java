package com.skydev.product_inventory_management.persistence.entity.enums;

import java.util.List;

import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;

import lombok.Getter;

@Getter
public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    NOT_SPECIFIED("Not specified");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender convertToGender(String gender){
        try{
            return Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException iae){
            throw new InvalidInputException(List.of("Gender is invalid"));
        }
    }

}

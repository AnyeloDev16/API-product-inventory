package com.skydev.product_inventory_management.presentation.validation;

import java.util.Arrays;

import com.skydev.product_inventory_management.presentation.validation.annotations.ValidEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String>{

    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null){
            return true;
        }

        return Arrays.stream(enumValues)
                        .map(Enum::name)
                        .anyMatch(enumName -> enumName.equals(value));

    }

}

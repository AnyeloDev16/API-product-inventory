package com.skydev.product_inventory_management.presentation.validation;

import com.skydev.product_inventory_management.persistence.repository.IProductRepository;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniqueProductName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !productRepository.existsByProductName(value);
    }

}

package com.skydev.product_inventory_management.presentation.validation;

import com.skydev.product_inventory_management.persistence.repository.ICategoryRepository;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniqueCategoryName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, String> {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !categoryRepository.existsByCategoryName(value);
    }
}

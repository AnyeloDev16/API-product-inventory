package com.skydev.product_inventory_management.presentation.validation;

import com.skydev.product_inventory_management.persistence.repository.IUserEntityRepository;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniquePhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {

    @Autowired
    private IUserEntityRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !userRepository.existsByPhone(value);
    }
}

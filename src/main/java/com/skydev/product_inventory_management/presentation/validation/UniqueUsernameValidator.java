package com.skydev.product_inventory_management.presentation.validation;

import com.skydev.product_inventory_management.persistence.repository.ICredentialEntityRepository;
import com.skydev.product_inventory_management.presentation.validation.annotations.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private ICredentialEntityRepository credentialRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !credentialRepository.existsByUsername(value);
    }
}

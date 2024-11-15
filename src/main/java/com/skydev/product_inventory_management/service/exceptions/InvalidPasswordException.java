package com.skydev.product_inventory_management.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidPasswordException extends AuthenticationException {

    public InvalidPasswordException(String message) {
        super(message);
    }

}

package com.skydev.product_inventory_management.service.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class UserNotActiveException extends AuthenticationException {

    public UserNotActiveException(String message) {
        super(message);
    }
}

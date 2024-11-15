package com.skydev.product_inventory_management.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    @Value("${message.register.successful}")
    public String REGISTER_SUCCESSFUL;

    @Value("${message.login.successful}")
    public String LOGIN_SUCCESSFUL;

    @Value("${error.message.role.not_found}")
    public String ROLE_NOT_FOUND;

    @Value("${error.message.user.id_not_found}")
    public String USER_ID_NOT_FOUND;

    @Value("${error.message.user.id_invalid}")
    public String USER_ID_INVALID;

    @Value("${error.message.credential.username_not_found}")
    public String CREDENTIALS_USERNAME_NOT_FOUND;

    @Value("${error.message.credential.invalid_password}")
    public String CREDENTIALS_INVALID_PASSWORD;

    @Value("${error.message.user.user_not_active}")
    public String USER_NOT_ACTIVE;

    @Value("${error.message.credential.invalid_credentials}")
    public  String INVALID_CREDENTIALS;

}

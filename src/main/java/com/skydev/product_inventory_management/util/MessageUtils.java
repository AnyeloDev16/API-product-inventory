package com.skydev.product_inventory_management.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

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
    public String INVALID_CREDENTIALS;

    @Value("${error.message.category.id_not_found}")
    public String CATEGORY_ID_NOT_FOUND;

    @Value("${error.message.category.id_invalid}")
    public String CATEGORY_ID_INVALID;

    @Value("${error.message.product.id_not_found}")
    public String PRODUCT_ID_NOT_FOUND;

    @Value("${error.message.product.id_invalid}")
    public String PRODUCT_ID_INVALID;

    @Value("${error.message.page.page_invalid}")
    public String PAGE_INVALID;

    @Value("${error.message.address.id_not_found}")
    public String ADDRESS_ID_NOT_FOUND;

    @Value("${error.message.address.id_invalid}")
    public String ADDRESS_ID_INVALID;

    @Value("${error.message.payment.id_provider_not_found}")
    public String PAYMENT_PROVIDER_ID_NOT_FOUND;

    @Value("${error.message.payment.id_method_not_found}")
    public String PAYMENT_METHOD_ID_NOT_FOUND;

    @Value("${error.message.payment.id_provider_invalid}")
    public String PAYMENT_PROVIDER_ID_INVALID;

    @Value("${error.message.payment.id_method_invalid}")
    public String PAYMENT_METHOD_ID_INVALID;

    @Value("${message.order.successful_purchase}")
    public String ORDER_SUCCESSFUL_PURCHASE;

    @Value("${error.message.order.id_not_found}")
    public String ORDER_ID_NOT_FOUND;

    @Value("${error.message.order.id_invalid}")
    public String ORDER_ID_INVALID;

    @Value("${error.message.cart.id_not_found}")
    public String CART_ID_NOT_FOUND;

}

package com.skydev.product_inventory_management.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TitleMessageUtil {

    @Value("${error.title.resource.not_found}")
    public String RESOURCE_NOT_FOUND;

    @Value("${error.title.invalid_input_provided}")
    public String INVALID_INPUT_PROVIDED;

    @Value("${error.title.auth.authentication_error}")
    public String AUTHENTICATION_ERROR;

    @Value("${error.title.forb.forbidden_access}")
    public String FORBIDDEN_ACCESS;

}

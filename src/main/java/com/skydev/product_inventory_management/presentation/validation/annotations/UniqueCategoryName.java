package com.skydev.product_inventory_management.presentation.validation.annotations;

import com.skydev.product_inventory_management.presentation.validation.UniqueCategoryNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCategoryNameValidator.class)
public @interface UniqueCategoryName {

    String message() default "Category name is already taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package com.devmate.apiserver.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueElementsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueElements {
    String message() default "Element Must Be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

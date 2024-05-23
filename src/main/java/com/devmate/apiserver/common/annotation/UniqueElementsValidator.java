package com.devmate.apiserver.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UniqueElementsValidator implements ConstraintValidator<UniqueElements, Collection<?>> {
    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        Set<Object> uniqueItems = new HashSet<>(value);
        return uniqueItems.size()==value.size();

    }
}

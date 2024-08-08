package com.first.flash.global.annotation;

import com.first.flash.account.auth.domain.Provider;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ProviderValidator implements ConstraintValidator<ValidProvider, String> {

    @Override
    public void initialize(final ValidProvider constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return Arrays.stream(Provider.values())
                     .anyMatch(role -> role.name().equals(value));
    }
}

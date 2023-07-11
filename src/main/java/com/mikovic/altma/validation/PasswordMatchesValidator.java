package com.mikovic.altma.validation;

import com.mikovic.altma.payload.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final SignUpRequest req = (SignUpRequest) obj;
        return req.getPassword().equals(req.getRePassword());
    }

}


package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.validator.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RentalUserDto user = (RentalUserDto) obj;
        return user.getUserPassword().equals(user.getMatchingPassword());
    }

}
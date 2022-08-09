package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.validator.annotation.ValidStrongPassword;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<ValidStrongPassword, String>{
    private static final String STRONG_PASSWORD_PATTERN = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final Pattern PATTERN = Pattern.compile(STRONG_PASSWORD_PATTERN);

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        return (validatePassword(password));
    }

    private boolean validatePassword(final String password) {
        Matcher matcher = PATTERN.matcher(password);
        return matcher.matches();
    }
}

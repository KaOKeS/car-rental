package com.dawidpater.project.carrental.validator.annotation;

import com.dawidpater.project.carrental.validator.EmailValidator;
import com.dawidpater.project.carrental.validator.StrongPasswordValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface ValidStrongPassword {

    String message() default "The password length must be greater than or equal to 8. \n" +
            "The password must contain one or more uppercase characters. \n" +
            "The password must contain one or more lowercase characters. \n" +
            "The password must contain one or more numeric values. \n" +
            "The password must contain one or more special characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

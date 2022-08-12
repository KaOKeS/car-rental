package com.dawidpater.project.carrental.validator.annotation;

import com.dawidpater.project.carrental.validator.DateNotPastValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateNotPastValidator.class)
@Documented
public @interface DateIsNotPast {
    String message() default "You selected date in past. Chose correct one.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

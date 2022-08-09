package com.dawidpater.project.carrental.validator.annotation;

import com.dawidpater.project.carrental.validator.DatesValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DatesValidator.class)
@Documented
public @interface StartDateLessThanEndDate {
    String message() default "Start date is bigger than end date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

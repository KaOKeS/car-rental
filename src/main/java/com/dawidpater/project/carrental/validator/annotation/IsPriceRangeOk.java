package com.dawidpater.project.carrental.validator.annotation;

import com.dawidpater.project.carrental.validator.PriceRangeValidator;

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
@Constraint(validatedBy = PriceRangeValidator.class)
@Documented
public @interface IsPriceRangeOk {
    String message() default "Minimum price is bigger than maximum price!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

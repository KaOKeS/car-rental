package com.dawidpater.project.carrental.validator.annotation;

import com.dawidpater.project.carrental.validator.CompanyDriverOrLicenseValidator;
import com.dawidpater.project.carrental.validator.PriceRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CompanyDriverOrLicenseValidator.class)
@Documented
public @interface CompanyDriverOrLicenseRequired {
    String message() default "If no company driver selected than driving license is mandatory!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

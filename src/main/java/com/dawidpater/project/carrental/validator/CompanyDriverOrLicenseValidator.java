package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.validator.annotation.CompanyDriverOrLicenseRequired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CompanyDriverOrLicenseValidator implements ConstraintValidator<CompanyDriverOrLicenseRequired, Object> {

    @Override
    public void initialize(final CompanyDriverOrLicenseRequired constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RentalRequestDto rentalRequest = (RentalRequestDto) obj;
        return (rentalRequest.isCompanyDriver()) || (!rentalRequest.getDrivingLicense().isEmpty());
    }

}
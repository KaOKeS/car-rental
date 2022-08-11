package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.validator.annotation.CompanyDriverOrLicenseRequired;
import com.dawidpater.project.carrental.validator.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompanyDriverOrLicenseValidator implements ConstraintValidator<CompanyDriverOrLicenseRequired, Object> {

    @Override
    public void initialize(final CompanyDriverOrLicenseRequired constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RentalRequestDto rentalRequest = (RentalRequestDto) obj;
        return (rentalRequest.isCompanyDriver()) ? true : (!rentalRequest.getDrivingLicense().isEmpty());
    }

}
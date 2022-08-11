package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.converter.IntegerTryParse;
import com.dawidpater.project.carrental.dto.webrequest.FilterCarsRequestDto;
import com.dawidpater.project.carrental.validator.annotation.IsPriceRangeOk;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceRangeValidator implements ConstraintValidator<IsPriceRangeOk, Object> {

    @Override
    public void initialize(final IsPriceRangeOk constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final FilterCarsRequestDto filterCarsRequestDto = (FilterCarsRequestDto) obj;
        Integer minPrice = IntegerTryParse.parse(filterCarsRequestDto.getMinPrice(),0);
        Integer maxPrice = IntegerTryParse.parse(filterCarsRequestDto.getMaxPrice(),0);
        return minPrice<maxPrice;
    }
}

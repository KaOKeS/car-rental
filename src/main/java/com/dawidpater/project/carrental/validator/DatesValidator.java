package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.dto.FilterCarsRequestDto;
import com.dawidpater.project.carrental.exception.IncorrectDateFormat;
import com.dawidpater.project.carrental.validator.annotation.StartDateLessThanEndDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class DatesValidator implements ConstraintValidator<StartDateLessThanEndDate, Object> {

    @Override
    public void initialize(final StartDateLessThanEndDate constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        LocalDateTimeFromStringConverter dateConverter = new LocalDateTimeFromStringConverter();
        final FilterCarsRequestDto filterCarsRequestDto = (FilterCarsRequestDto) obj;
        String startDateString = filterCarsRequestDto.getStartDate();
        String endDateString = filterCarsRequestDto.getEndDate();
        if(startDateString==null || startDateString.isEmpty() || endDateString==null || endDateString.isEmpty())
            return false;
        Pattern datePattern = Pattern.compile("\\d\\d-\\d\\d-\\d\\d\\d\\d");
        if(!datePattern.matcher(startDateString).find() || !datePattern.matcher(endDateString).find())
            throw new IncorrectDateFormat();
        LocalDateTime startDate = dateConverter.getDate(startDateString, "00:00");
        LocalDateTime endDate = dateConverter.getDate(endDateString, "23:59");
        return startDate.isBefore(endDate);
    }
}

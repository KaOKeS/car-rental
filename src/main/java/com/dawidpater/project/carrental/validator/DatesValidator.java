package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.contract.ComparableStringDates;
import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.exception.IncorrectDateFormatException;
import com.dawidpater.project.carrental.validator.annotation.StartDateLessThanEndDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Component
@Slf4j
public class DatesValidator implements ConstraintValidator<StartDateLessThanEndDate, Object> {

    @Override
    public void initialize(final StartDateLessThanEndDate constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        LocalDateTimeFromStringConverter dateConverter = new LocalDateTimeFromStringConverter();
        final ComparableStringDates objWithDatesToCompare = (ComparableStringDates) obj;
        String startDateString = objWithDatesToCompare.getStartDate();
        String endDateString = objWithDatesToCompare.getEndDate();
        if(startDateString==null || startDateString.isEmpty() || endDateString==null || endDateString.isEmpty())
            return false;
        Pattern datePattern = Pattern.compile("\\d\\d-\\d\\d-\\d\\d\\d\\d");
        if(!datePattern.matcher(startDateString).find() || !datePattern.matcher(endDateString).find()){
            log.debug("Dates are not in correct format. Start date={} and End date={}",startDateString,endDateString);
            throw new IncorrectDateFormatException();
        }
        LocalDateTime startDate = dateConverter.getDate(startDateString, "00:00");
        LocalDateTime endDate = dateConverter.getDate(endDateString, "23:59");
        return startDate.isBefore(endDate);
    }
}

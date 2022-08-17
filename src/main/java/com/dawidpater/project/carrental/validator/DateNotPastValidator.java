package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.validator.annotation.DateIsNotPast;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateNotPastValidator  implements ConstraintValidator<DateIsNotPast, String> {

    @Override
    public boolean isValid(final String stringDate, final ConstraintValidatorContext context) {
        if(stringDate==null || stringDate.isEmpty())
            return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return (validateDateNotPast(localDate));
    }

    private boolean validateDateNotPast(final LocalDate date) {
        LocalDate today = LocalDateTime.now().toLocalDate();
        return date.isAfter(today) || date.isEqual(today);
    }
}
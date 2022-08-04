package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.exception.EndDateBeforeStartDateException;
import com.dawidpater.project.carrental.exception.NotCorrectMinMaxValueException;
import com.dawidpater.project.carrental.exception.NullOrEmptyDateException;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ReqParamsValidator {
    public boolean isDoubleAndMinMaxRangeValid(String minValue, String maxValue) throws NotCorrectMinMaxValueException {
        if(minValue==null || minValue.isEmpty() || maxValue==null || maxValue.isEmpty())
            return false;
        else if(Double.parseDouble(minValue)>Double.parseDouble(maxValue))
            throw new NotCorrectMinMaxValueException();
        return true;
    }

    public boolean isDateValid(String startDateString, String endDateString) throws NullOrEmptyDateException,EndDateBeforeStartDateException {
        Pattern datePattern = Pattern.compile("\\d\\d-\\d\\d-\\d\\d\\d\\d");
        if(startDateString==null || endDateString==null || !datePattern.matcher(startDateString).find() || !datePattern.matcher(endDateString).find())
            throw new NullOrEmptyDateException();

        LocalDateTimeFromStringConverter dateConverter = new LocalDateTimeFromStringConverter();
        LocalDateTime startDate = dateConverter.getDate(startDateString,"00:00");
        LocalDateTime endDate = dateConverter.getDate(endDateString,"23:59");
        if(startDate.isAfter(endDate))
            throw new EndDateBeforeStartDateException();
        return true;
    }

    public boolean isOrderByValid(String orderBy){
            return !(orderBy == null  || orderBy.isEmpty());
    }
}

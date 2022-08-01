package com.dawidpater.project.carrental.validator;

import com.dawidpater.project.carrental.exception.NotCorrectMinMaxValueException;

public class ReqParamsValidator {
    public boolean validateDoubleAndMinMaxRange(String minValue, String maxValue) throws NotCorrectMinMaxValueException {
        if(minValue==null || minValue.isEmpty() || maxValue==null || maxValue.isEmpty())
            return false;
        else if(Double.parseDouble(minValue)>Double.parseDouble(maxValue))
            throw new NotCorrectMinMaxValueException();
        return true;
    }
}

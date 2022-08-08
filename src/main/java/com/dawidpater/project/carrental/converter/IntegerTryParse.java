package com.dawidpater.project.carrental.converter;

public class IntegerTryParse {
    public static Integer parse(String obj, int defaultValue) {
        Integer retVal;
        if(obj==null || obj.isEmpty())
            return defaultValue;
        try {
            retVal = Integer.parseInt(obj);
        } catch (NumberFormatException nfe) {
            retVal = defaultValue;
        }
        return retVal;
    }
}

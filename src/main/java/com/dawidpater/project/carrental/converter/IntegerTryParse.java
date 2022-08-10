package com.dawidpater.project.carrental.converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntegerTryParse {
    public static Integer parse(String obj, int defaultValue) {
        log.info("Trying to parse {} to int",obj);
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

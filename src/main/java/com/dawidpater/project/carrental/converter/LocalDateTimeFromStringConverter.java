package com.dawidpater.project.carrental.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class LocalDateTimeFromStringConverter {
    public static final String DAY_START_TIME = "00:00";
    public static final String DAY_END_TIME = "23:59";

    public LocalDateTime getDate(String stringDate, String stringTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        log.debug("Trying to convert date string: {} and time string {} to LocalDateTime",stringDate,stringTime);
        return LocalDateTime.parse(stringDate + " " + stringTime,formatter);
    }
}

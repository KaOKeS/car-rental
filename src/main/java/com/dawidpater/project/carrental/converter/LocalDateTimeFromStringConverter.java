package com.dawidpater.project.carrental.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFromStringConverter {

    public LocalDateTime getDate(String stringDate, String stringTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime date = LocalDateTime.parse(stringDate + " " + stringTime,formatter);
        return date;
    }
}

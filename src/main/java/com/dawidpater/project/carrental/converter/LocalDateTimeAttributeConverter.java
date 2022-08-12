package com.dawidpater.project.carrental.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Component
@Converter(autoApply = true)
@Slf4j
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
        log.debug("Converting to database column value: {}",locDateTime);
        return locDateTime == null ? null : Timestamp.valueOf(locDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        log.debug("Converting to entity value: {}",sqlTimestamp);
        return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
    }
}
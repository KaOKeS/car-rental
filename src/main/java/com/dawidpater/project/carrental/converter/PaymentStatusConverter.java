package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.entity.constant.PaymentStatus;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Component
@Converter(autoApply = true)
public class PaymentStatusConverter  implements AttributeConverter<PaymentStatus, String> {
    @Override
    public String convertToDatabaseColumn(PaymentStatus paymentStatus) {
        if (paymentStatus == null) {
            return null;
        }
        return paymentStatus.name();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return Stream.of(PaymentStatus.values()).
                filter(paymentStatus -> paymentStatus.name().equalsIgnoreCase(s)).
                findFirst().orElseThrow(IllegalArgumentException::new);
    }
}

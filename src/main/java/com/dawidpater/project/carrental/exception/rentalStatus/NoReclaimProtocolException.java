package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class NoReclaimProtocolException extends RentalStatusException {
    public NoReclaimProtocolException(String message, Long rentalId) {
        super(message, rentalId);
    }
}

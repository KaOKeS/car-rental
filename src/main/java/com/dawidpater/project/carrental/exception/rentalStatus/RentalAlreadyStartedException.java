package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalAlreadyStartedException extends RentalStatusException {
    public RentalAlreadyStartedException(String message, Long rentalId) {
        super(message, rentalId);
    }
}

package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotEvenStartedException extends RentalStatusException {
    public RentalNotEvenStartedException(String message, Long rentalId) {
        super(message, rentalId);
    }
}

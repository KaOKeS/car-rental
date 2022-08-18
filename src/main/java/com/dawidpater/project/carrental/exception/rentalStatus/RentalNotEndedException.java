package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotEndedException extends RentalStatusException {
    public RentalNotEndedException(String message, Long rentalId) {
        super(message, rentalId);
    }
}

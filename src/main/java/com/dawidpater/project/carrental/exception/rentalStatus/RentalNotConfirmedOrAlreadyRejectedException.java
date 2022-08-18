package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotConfirmedOrAlreadyRejectedException extends RentalStatusException{
    public RentalNotConfirmedOrAlreadyRejectedException(String message, Long rentalId) {
        super(message, rentalId);
    }
}

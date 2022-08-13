package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotConfirmedOrAlreadyRejectedException extends IllegalStateException{
    public RentalNotConfirmedOrAlreadyRejectedException(String msg) {
        super(msg);
    }
}

package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotPaidOrNotEndedException extends IllegalStateException{
    public RentalNotPaidOrNotEndedException(String msg) {
        super(msg);
    }
}

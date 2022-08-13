package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotEvenStartedException extends IllegalStateException{
    public RentalNotEvenStartedException(String msg) {
        super(msg);
    }
}

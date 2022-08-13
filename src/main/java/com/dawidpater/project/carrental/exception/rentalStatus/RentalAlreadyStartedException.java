package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalAlreadyStartedException extends IllegalStateException{
    public RentalAlreadyStartedException(String msg) {
        super(msg);
    }
}

package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotEndedException extends IllegalStateException{
    public RentalNotEndedException(String msg){
        super(msg);
    }
}

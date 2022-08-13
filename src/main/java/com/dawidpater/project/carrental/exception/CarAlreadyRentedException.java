package com.dawidpater.project.carrental.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CarAlreadyRentedException extends IllegalStateException{
    public CarAlreadyRentedException(String msg){
        super(msg);
    }
}

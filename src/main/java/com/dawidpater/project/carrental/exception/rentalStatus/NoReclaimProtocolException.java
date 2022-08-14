package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class NoReclaimProtocolException extends IllegalStateException{
    public NoReclaimProtocolException(String msg) {
        super(msg);
    }
}

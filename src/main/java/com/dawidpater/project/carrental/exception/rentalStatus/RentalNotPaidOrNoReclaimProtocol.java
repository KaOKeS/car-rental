package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotPaidOrNoReclaimProtocol extends IllegalStateException{

    public RentalNotPaidOrNoReclaimProtocol(String msg) {
        super(msg);
    }
}

package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalNotPaidOrNoReclaimProtocol extends RentalStatusException {

    public RentalNotPaidOrNoReclaimProtocol(String message, Long rentalId) {
        super(message, rentalId);
    }
}

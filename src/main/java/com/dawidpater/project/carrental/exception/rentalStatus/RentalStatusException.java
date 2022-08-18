package com.dawidpater.project.carrental.exception.rentalStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@Setter
@Getter
@AllArgsConstructor
public class RentalStatusException extends RuntimeException{
    private Long rentalId;

    public RentalStatusException(String message,Long rentalId) {
        super(message);
        this.rentalId=rentalId;
    }


}

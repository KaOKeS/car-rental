package com.dawidpater.project.carrental.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InvoiceDto {
    private long id;
    private long rentalValue;
    private long additionalCost;
    private RentalDto rental;
}

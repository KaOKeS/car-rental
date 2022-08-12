package com.dawidpater.project.carrental.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InvoiceDto {
    private long id;
    private double rentalValue;
    private double additionalCost;
    private String status;
    private RentalDto rental;
}

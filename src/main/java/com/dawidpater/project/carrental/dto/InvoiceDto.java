package com.dawidpater.project.carrental.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InvoiceDto {
    private long id;
    private BigDecimal rentalValue;
    private BigDecimal additionalCost;
    private BigDecimal damageCost;
    private String status;
    private RentalDto rental;
}

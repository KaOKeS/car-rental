package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.entity.constant.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private RentalDto rental;
    private PaymentStatus basicPaymentStatus;
    private PaymentStatus damagePaymentStatus;
}

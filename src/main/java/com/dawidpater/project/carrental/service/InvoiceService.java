package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Invoice;
import com.dawidpater.project.carrental.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;

@Service
@AllArgsConstructor
@Slf4j
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public BigDecimal calculateRentalCosts(Car car, LocalDateTime startDate, LocalDateTime endDate){
        Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
        int amountOfDays = period.getDays() + 1;
        BigDecimal rentalCosts = car.getPrice().multiply(new BigDecimal(amountOfDays));
        return rentalCosts;
    }

    public BigDecimal calculateAdditionalCosts(LocalDateTime startDate, LocalDateTime endDate, boolean withDriver, BigDecimal driverPrice){
        Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
        int amountOfDays = period.getDays() + 1;
        BigDecimal extraCosts = (withDriver) ? driverPrice.multiply(new BigDecimal(amountOfDays)) : new BigDecimal(0);
        return extraCosts;
    }

    public Invoice saveInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }
}

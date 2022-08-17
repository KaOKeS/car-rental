package com.dawidpater.project.carrental.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentalDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private FeedbackDto feedbackDto;
    private RentalUserDto rentalUserDto;
    private CarDto carDto;
    private boolean companyDriver;
    private boolean rejected;
    private String rejectionReason;
    private boolean confirmed;
    private boolean started;
    private boolean ended;
    private boolean carDamaged;
    private String drivingLicense;
    private InvoiceDto invoiceDto;
    private boolean closed;
    private String reclaimProtocol;
}

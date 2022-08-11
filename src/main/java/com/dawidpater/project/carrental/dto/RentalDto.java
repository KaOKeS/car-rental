package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.entity.RentalUser;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    private String damageDescription;
    private String drivingLicense;
    private InvoiceDto invoiceDto;
}

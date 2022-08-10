package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.entity.RentalUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}

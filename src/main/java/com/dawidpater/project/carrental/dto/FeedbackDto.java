package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.entity.Rental;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDto {
    private Long id;
    private String content;
    private Float rate;
    private LocalDateTime feedbackDate;
    private RentalDto rentalDto = null;
}

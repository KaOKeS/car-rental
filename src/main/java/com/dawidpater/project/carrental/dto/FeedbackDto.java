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
public class FeedbackDto {
    private Long id;
    private String content;
    private Float rate;
    private LocalDateTime feedbackDate;
    private RentalDto rentalDto;
}

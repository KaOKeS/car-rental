package com.dawidpater.project.carrental.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "rentalDto")
public class FeedbackDto {
    private Long id;
    private String content;
    private Float rate;
    private LocalDateTime feedbackDate;
    private RentalDto rentalDto;
}

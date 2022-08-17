package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.FeedbackConverter;
import com.dawidpater.project.carrental.converter.RentalConverter;
import com.dawidpater.project.carrental.converter.RentalUserConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FeedbackDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackConverter feedbackConverter;
    private final RentalConverter rentalConverter;
    private final CarConverter carConverter;
    private final RentalUserConverter rentalUserConverter;

    public List<FeedbackDto> getLatestFeedbacksLimitedTo(int amount){
        log.debug("Trying to receive feedbacks from database");
        List<Feedback> latestFeedbacksLimited = feedbackRepository.getFeedbacksOrderByDateDesc();
        log.debug("Trying to make sublist of feedbacks to size={}",amount);
        if(latestFeedbacksLimited.size()>amount){
            latestFeedbacksLimited = latestFeedbacksLimited.subList(0,amount);
        }

        log.debug("Building List of FeedbackDto from List<Feedback>");
        List<FeedbackDto> feedbackDtos = latestFeedbacksLimited.stream().map(feedback -> {
            FeedbackDto feedbackDto = feedbackConverter.entityToDto(feedback);
            feedbackDto.setRentalDto(rentalConverter.entityToDto(feedback.getRental()));
            feedbackDto.getRentalDto().setRentalUserDto(rentalUserConverter.entityToDto(feedback.getRental().getRentalUser()));
            feedbackDto.getRentalDto().setCarDto((carConverter.entityToDto(feedback.getRental().getCar())));
            return feedbackDto;
        }).toList();
        log.debug("Returning List of FeedbackDto");
        return feedbackDtos;
    }

    //TODO: Feedback adding
    public Feedback save(){
        Feedback review = new Feedback();
        review.setContent("elo");
        review.setRate(4.5F);
        review.setFeedbackDate(LocalDateTime.now());
        return review;
    }
}

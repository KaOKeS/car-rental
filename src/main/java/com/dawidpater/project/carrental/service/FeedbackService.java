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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final RentalUserRepository rentalUserRepository;
    private final FeedbackConverter feedbackConverter;
    private final RentalConverter rentalConverter;
    private final CarConverter carConverter;
    private final RentalUserConverter rentalUserConverter;

    public List<FeedbackDto> getLatestFeedbacksLimitedTo(int amount){
        List<Feedback> latestFeedbacksLimited = feedbackRepository.getLatestFeedbacksLimitedTo(amount);
        List<FeedbackDto> feedbackDtos = feedbackConverter.entityToDto(latestFeedbacksLimited);
        List<Long> feedbackIds = latestFeedbacksLimited.stream().map(Feedback::getId).collect(Collectors.toList());
        List<Rental> rentalsAccordingToReviews = rentalRepository.findByFeedbackIdIn(feedbackIds);
        List<RentalDto> rentalDtos = rentalConverter.entityToDto(rentalsAccordingToReviews);
        List<Long> rentalsIds = rentalsAccordingToReviews.stream().map(Rental::getId).collect(Collectors.toList());
        List<Car> carsAccordingToReviews = carRepository.findByRentalIdIn(rentalsIds);
        List<CarDto> carDtos = carConverter.entityToDto(carsAccordingToReviews);
        List<RentalUser> usersAccordingToReviews = rentalUserRepository.findByRentalIdIn(rentalsIds);
        List<RentalUserDto> userDtos =  rentalUserConverter.entityToDto(usersAccordingToReviews);
        for(int i = 0; i < feedbackDtos.size(); i++){
            rentalDtos.get(i).setCarDto(carDtos.get(i));
            rentalDtos.get(i).setRentalUserDto(userDtos.get(i));
            feedbackDtos.get(i).setRentalDto(rentalDtos.get(i));
        }
        return feedbackDtos;
    }

    public Feedback save(){
        Feedback review = new Feedback();
        review.setContent("elo");
        review.setRate(4.5F);
        review.setFeedbackDate(LocalDateTime.now());
        return review;
    }
}

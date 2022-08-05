package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.FeedbackConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FeedbackDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class MainPageController {
    private final CarService carService;
    private final FeedbackService feedbackService;
    private final CarConverter carConverter;

    @GetMapping("/")
    public String goToMain(Model model){
        List<Car> bestCarFromEachType = carService.getBestCarFromEachType();
        List<CarDto> carsDto = carConverter.entityToDto(bestCarFromEachType);

        List<FeedbackDto> latestReviewsLimited = feedbackService.getLatestFeedbacksLimitedTo(5);

        model.addAttribute("cars", carsDto);
        model.addAttribute("feedbacks", latestReviewsLimited);
        return "main";
    }
}

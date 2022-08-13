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
import com.dawidpater.project.carrental.service.RentalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class MainPageController {
    private final CarService carService;
    private final FeedbackService feedbackService;
    private final CarConverter carConverter;
    private final RentalService rentalService;

    @GetMapping("/")
    public String goToMain(Model model, HttpServletRequest request){
        log.info("Preparing Top 3 cars to dispaly");
        List<CarDto> carsDto = carService.getBestCarFromEachType();
        model.addAttribute("cars", carsDto);

        log.info("Preparing latest reviews to display");
        List<FeedbackDto> latestReviewsLimited = feedbackService.getLatestFeedbacksLimitedTo(5);
        model.addAttribute("feedbacks", latestReviewsLimited);

        log.info("If user is Admin or Manager then check how many new rentals exists");
        int newRentals = (request.isUserInRole("MANAGER") || request.isUserInRole("ADMIN")) ? rentalService.getAmountOfNotConfirmedRentals() : 0;
        model.addAttribute("newRentals", newRentals);

        log.info("Returning view");
        return "main";
    }
}

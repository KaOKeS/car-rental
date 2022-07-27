package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@AllArgsConstructor
public class MainPageController {
    private final CarService carService;
    private final FeedbackService feedbackService;

    @GetMapping
    public String writeMainPage(Model model){
        model.addAttribute("cars",carService.getBestCarFromEachType());
        model.addAttribute("feedbacks",feedbackService.getLatestReviewsLimitedTo(5));
        return "main";
    }
}

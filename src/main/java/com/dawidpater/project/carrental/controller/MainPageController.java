package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@AllArgsConstructor
public class MainPageController {
    private final CarService carService;
    private final FeedbackService feedbackService;

    @GetMapping("/")
    public String goToMain(Model model){
        model.addAttribute("cars",carService.getBestCarFromEachType());
        model.addAttribute("feedbacks",feedbackService.getLatestReviewsLimitedTo(5));
        return "main";
    }

    @GetMapping("/login")
    public String goToLogin(Model model){
        return "login";
    }

    @GetMapping("/register")
    public String goToRegister(@ModelAttribute("newuser") RentalUser rentalUser, Model model){
        return "register";
    }
}

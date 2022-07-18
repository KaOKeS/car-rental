package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainPageController {
    private final CarService carService;
    private final ReviewService reviewService;

    public MainPageController(CarService carService, ReviewService reviewService) {
        this.carService = carService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String getCar(Model model){
        model.addAttribute("cars",carService.getCarsWithWebContent());
        model.addAttribute("reviews",reviewService.findAll());
        return "main";
    }
}

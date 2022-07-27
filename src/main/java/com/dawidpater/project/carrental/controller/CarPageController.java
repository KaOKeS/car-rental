package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
@AllArgsConstructor
public class CarPageController {
    private final CarService carService;

    @GetMapping
    public String writeCarPage(Model model){
        model.addAttribute("cars",carService.getBestCarFromEachType());
        return "car";
    }
}

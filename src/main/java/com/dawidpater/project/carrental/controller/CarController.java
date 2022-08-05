package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public String displayCars(Model model){
        List<String> allCarsTypes = carService.getAllCarsTypes();
        model.addAttribute("restRequestUrl","/cars");
        model.addAttribute("carTypes", allCarsTypes);
        return "car";
    }
}

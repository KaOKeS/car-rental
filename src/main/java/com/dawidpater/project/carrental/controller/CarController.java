package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.model.Car;
import com.dawidpater.project.carrental.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String getCar(Model model){
        model.addAttribute("cars",carService.getTopCars());
        return "car";
    }
}

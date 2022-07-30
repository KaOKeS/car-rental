package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public String displayCars(Model model){
        model.addAttribute("restRequestUrl","/cars");
        model.addAttribute("carTypes",carService.getAllCarsTypes());
        return "car";
    }

    @GetMapping(path = "/cars", params = "type")
    public String displayCars(@RequestParam("type") String type, Model model){
        model.addAttribute("restRequestUrl","/cars?type="+type);
        return "car";
    }
}

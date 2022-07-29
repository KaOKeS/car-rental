package com.dawidpater.project.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarController {
    @GetMapping("/cars")
    public String displayCars(Model model){
        model.addAttribute("restRequestUrl","/cars");
        return "car";
    }

    @GetMapping(path = "/cars", params = "type")
    public String displayCars(@RequestParam("type") String type, Model model){
        model.addAttribute("restRequestUrl","/cars?type="+type);
        return "car";
    }
}

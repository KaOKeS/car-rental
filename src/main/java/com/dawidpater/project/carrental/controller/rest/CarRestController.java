package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CarRestController {
    private final CarService carService;

    @GetMapping(value = "/cars")
    public List<Car> getRequestedCars(@RequestParam Map<String,String> allRequestParams, ModelMap model){
        List<Car> requestedCars = carService.getCarsAsRequested(allRequestParams);
        return requestedCars;
    }
}

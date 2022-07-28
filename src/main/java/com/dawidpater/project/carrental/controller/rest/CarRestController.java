package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
@Data
public class CarRestController {
    private final CarService carService;

    @GetMapping
    public List<Car> getAllCars(){
        List<Car> allCars = carService.getAllCars();
        return allCars;
    }
}

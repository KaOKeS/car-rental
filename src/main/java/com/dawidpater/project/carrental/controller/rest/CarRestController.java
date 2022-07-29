package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Data
public class CarRestController {
    private final CarService carService;

    @GetMapping(value = "/cars")
    public List<Car> getAllCars(){
        List<Car> allCars = carService.getAllCars();
        return allCars;
    }

    @GetMapping(path = "/cars", params = "type")
    public List<Car> getCarsByType(@RequestParam(name = "type") String type, Model model){
        model.addAttribute("","");
        List<Car> cars = carService.findByType(type);
        return cars;
    }
}

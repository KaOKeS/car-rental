package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarConverter carConverter;

    @GetMapping("/cars")
    public String displayCars(Model model){
        List<String> allCarsTypes = carService.getAllCarsTypes();
        model.addAttribute("restRequestUrl","/cars");
        model.addAttribute("carTypes", allCarsTypes);
        return "car";
    }

    @GetMapping("/addCar")
    public String showNewCarForm(@ModelAttribute CarDto carDto, Model model){
        return "new_car";
    }

    @GetMapping("/updateCar/{id}")
    public String showUpdateForm(@PathVariable(value = "id") Long id, Model model){
        Car car = carService.getCarById(id);
        CarDto carDto = carConverter.entityToDto(car);
        model.addAttribute("carDto",carDto);
        return "update_car";
    }

    @PostMapping("/saveCar")
    public String saveCar(@ModelAttribute CarDto carDto, Model model){
        Car carEntity = carConverter.dtoToEntity(carDto);
        carService.addCar(carEntity);
        return "redirect:/addCar?success";
    }
}

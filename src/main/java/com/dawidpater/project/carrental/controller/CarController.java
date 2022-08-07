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

    @GetMapping("/management/admin/addCar")
    public String showNewCarForm(@ModelAttribute CarDto carDto, Model model){
        return "new_car";
    }

    @GetMapping("/management/admin/updateCar/{id}")
    public String showUpdateForm(@PathVariable(value = "id") Long id, Model model){
        Car car = carService.getCarById(id);
        CarDto carDto = carConverter.entityToDto(car);
        model.addAttribute("carDto",carDto);
        return "new_car";
    }

    @PostMapping("/management/admin/saveCar")
    public String saveCar(@ModelAttribute CarDto carDto, Model model){
        Car carEntity = carConverter.dtoToEntity(carDto);
        carService.addCar(carEntity);
        return "redirect:/management/admin/addCar?success";
    }

    @GetMapping("/management/admin/deleteCar/{id}")
    public String deleteCar(@PathVariable(value = "id") Long id, Model model){
        carService.deleteCarById(id);
        return "redirect:/cars?deletionSucced";
    }
}

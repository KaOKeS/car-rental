package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.dto.CarDto;
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

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CarRestController {
    private final CarService carService;
    private final CarConverter converter;

    @GetMapping(value = "/cars")
    public List<CarDto> getRequestedCars(@RequestParam Map<String,String> allRequestParams, ModelMap model){
        List<Car> requestedCars = carService.getCarsAsRequested(allRequestParams);
        List<CarDto> requestedCarsDto = converter.entityToDto(requestedCars);
        return requestedCarsDto;
    }
}

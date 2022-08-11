package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.webrequest.FilterCarsRequestDto;
import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
public class CarRestController {
    private final CarService carService;

    @GetMapping
    public List<CarDto> getAllCars(@RequestParam(required = false) String perPage,
                                   @RequestParam(required = false) String pageNumber){
        Page<CarDto> requestedCars = carService.getAllCars(pageNumber,perPage);
        return requestedCars.getContent();
    }

    @GetMapping(params = "perPage")
    public List<CarDto> getRequestedCars(@ModelAttribute FilterCarsRequestDto filterCarsRequestDto,
                                         @RequestParam(required = false) String perPage,
                                         @RequestParam(required = false) String pageNumber){
        Page<CarDto> requestedCars = carService.getCarsAsRequested(filterCarsRequestDto,pageNumber,perPage);
        return requestedCars.getContent();
    }

    //TODO: play with spring security
    @PostMapping
    public String addNewCar(@RequestBody CarDto carDto){
        //Car car = converter.dtoToEntity(carDto);
        //car = carService.addCar(car);
        return "Does it work?";
    }
}

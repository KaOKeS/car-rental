package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.IntegerTryParse;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FilterCarsRequestDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

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

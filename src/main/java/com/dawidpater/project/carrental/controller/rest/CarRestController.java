package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.IntegerTryParse;
import com.dawidpater.project.carrental.dto.CarDto;
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
    private final CarConverter converter;

    @GetMapping
    public List<CarDto> getRequestedCars(@RequestParam Map<String,String> allRequestParams){
        int reqPageNumber = IntegerTryParse.parse(allRequestParams.get("pageNumber"),1)-1;
        int reqPageSize = IntegerTryParse.parse(allRequestParams.get("perPage"),5);
        Page<Car> requestedCars = carService.getCarsAsRequested(allRequestParams,reqPageNumber,reqPageSize);
        List<CarDto> requestedCarsDto = converter.entityToDto(requestedCars.getContent());
        return requestedCarsDto;
    }

    @PostMapping
    public String addNewCar(@RequestBody CarDto carDto){
        //Car car = converter.dtoToEntity(carDto);
        //car = carService.addCar(car);
        return "Does it work?";
    }
}

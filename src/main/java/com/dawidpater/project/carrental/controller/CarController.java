package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.IntegerTryParse;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarConverter carConverter;

    @GetMapping("/cars")
    public String displayCars(@RequestParam(required = false) Map<String,String> allRequestParams,
                              Model model,
                              HttpServletRequest request){
        List<String> allCarsTypes = carService.getAllCarsTypes();
        int reqPageNumber = IntegerTryParse.parse(allRequestParams.get("pageNumber"),1)-1;
        int reqPageSize = IntegerTryParse.parse(allRequestParams.get("perPage"),5);
        Page<Car> carsAsRequested = carService.getCarsAsRequested(allRequestParams, reqPageNumber, reqPageSize);
        model.addAttribute("cars",carsAsRequested);
        model.addAttribute("totalItems",carsAsRequested.getTotalElements());
        model.addAttribute("totalPages",carsAsRequested.getTotalPages());
        model.addAttribute("currentPage",reqPageNumber);
        model.addAttribute("carTypes", allCarsTypes);
        model.addAttribute("isAdmin",request.isUserInRole("ADMIN"));
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

package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FilterCarsRequestDto;
import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.validator.UserRoleValdation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@AllArgsConstructor
@Slf4j
public class CarController {

    private final CarService carService;
    private final CarConverter carConverter;

    @GetMapping("/cars")
    public String displayCars(@RequestParam(required = false) String perPage,
                              @RequestParam(required = false) String pageNumber,
                              Model model,
                              HttpServletRequest request){
        log.debug("Fetching all car types.");
        List<String> allCarsTypes = carService.getAllCarsTypes();

        log.debug("Passing pageNumber={} and perPage={} to carService",pageNumber,perPage);
        Page<CarDto> carsAsRequested = carService.getAllCars(pageNumber,perPage);

        log.debug("Adding attributes to model");
        model.addAttribute("cars",carsAsRequested);
        model.addAttribute("totalItems",carsAsRequested.getTotalElements());
        model.addAttribute("totalPages",carsAsRequested.getTotalPages());
        model.addAttribute("carTypes", allCarsTypes);
        model.addAttribute("filterCarsRequestDto", new FilterCarsRequestDto());

        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("Displaying cars for user with role {}", currentUserRoles.toArray().toString());
        model.addAttribute("isAdmin",request.isUserInRole("ADMIN"));
        return "car";
    }

    @GetMapping(value = "/cars", params = "perPage")
    public String displayCars(@Valid @ModelAttribute FilterCarsRequestDto filterCarsRequestDto,
                              BindingResult result,
                              @RequestParam(required = false) String perPage,
                              @RequestParam(required = false) String pageNumber,
                              Model model,
                              HttpServletRequest request){

        if (result.hasErrors()) {
            log.debug("Validation failure occured to filterCarsRequestDto = {}",filterCarsRequestDto);
            return "/car";
        }

        log.debug("Fetching cars according to request.");
        List<String> allCarsTypes = carService.getAllCarsTypes();

        log.debug("Passing pageNumber={} and perPage={} to carService",pageNumber,perPage);
        Page<CarDto> carsAsRequested = carService.getCarsAsRequested(filterCarsRequestDto,pageNumber,perPage);

        log.debug("Adding attributes to model");
        model.addAttribute("cars",carsAsRequested);
        model.addAttribute("totalItems",carsAsRequested.getTotalElements());
        model.addAttribute("totalPages",carsAsRequested.getTotalPages());
        model.addAttribute("carTypes", allCarsTypes);
        model.addAttribute("filterCarsRequestDto", new FilterCarsRequestDto());

        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("Displaying cars for user with role {}", currentUserRoles.toArray().toString());
        model.addAttribute("isAdmin",request.isUserInRole("ADMIN"));
        return "car";
    }



    @GetMapping("/management/admin/addCar")
    public String showNewCarForm(@ModelAttribute CarDto carDto, Model model, HttpServletRequest request){
        log.info("Showing add car webpage");
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("Displaying admin add car page for user with role {}", currentUserRoles.toArray().toString());
        return "new_car";
    }

    @GetMapping("/management/admin/updateCar/{id}")
    public String showUpdateForm(@PathVariable(value = "id") Long id, Model model,HttpServletRequest request){
        log.info("Showing car update webpage with carId={}",id);
        CarDto carDto = carService.getCarById(id);
        log.info("Car with requested id={} is {}",id,carDto);
        model.addAttribute("carDto",carDto);
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("Displaying admin update car page for user with role {}", currentUserRoles.toArray().toString());
        return "new_car";
    }

    @PostMapping("/management/admin/saveCar")
    public String saveCar(@Valid @ModelAttribute CarDto carDto, BindingResult result, Model model, HttpServletRequest request, RedirectAttributes attr){
        log.debug("Car dto provided by admin: {}",carDto);
        if (result.hasErrors()) {
            log.debug("Validation failure");
            return "new_car";
        }
        carService.addCar(carDto);
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("Displaying admin update car page for user with role {}", currentUserRoles.toArray().toString());
        return "redirect:/management/admin/addCar?success";
    }

    @GetMapping("/management/admin/deleteCar/{id}")
    public String deleteCar(@PathVariable(value = "id") Long id, Model model,HttpServletRequest request){
        log.debug("Admin deleting car with id={}",id);
        carService.deleteCarById(id);
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("Displaying admin update car page for user with role {}", currentUserRoles.toArray().toString());
        return "redirect:/cars?deletionSucced";
    }
}

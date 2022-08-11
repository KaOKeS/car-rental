package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/rent")
public class RentController {
    private final CarService carService;
    private final RentalService rentalService;

    @GetMapping(value = "/{id}")
    public String showRentFormFilled(@PathVariable Long id,
                                     @ModelAttribute RentalRequestDto rentalRequestDto,
                                     Model model){
        model.addAttribute("carDto",carService.getCarById(id));
        return "rent";
    }

    @PostMapping
    public String makeRental(@Valid @ModelAttribute RentalRequestDto rentalRequestDto,
                             BindingResult result,
                             @ModelAttribute CarDto carDto,
                             Model model){
        if(result.hasErrors()){
            return "rent";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser)auth.getPrincipal();
        rentalService.makeRental(rentalRequestDto,carDto,user);
        return "redirect:/user/rental";
    }
}

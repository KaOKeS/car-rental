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
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/rent")
public class RentController {
    private final CarService carService;
    private final RentalService rentalService;

    @GetMapping(value = "/{id}")
    public String showRentFormFilled(@PathVariable Long id, Model model){
        model.addAttribute("car",carService.getCarById(id));
        model.addAttribute("rentalRequestDto",new RentalRequestDto());
        return "rent";
    }

    @PostMapping
    public String makeRental(@ModelAttribute RentalRequestDto rentalRequestDto,
                             @ModelAttribute CarDto carDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser)auth.getPrincipal();
        rentalService.makeRental(rentalRequestDto,carDto);
        return "redirect:/user/rental";
    }
}

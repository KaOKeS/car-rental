package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.service.RentalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/rental")
public class RentalController {
    private final CarService carService;
    private final RentalService rentalService;

    @GetMapping(value = "/{id}")
    public String showRentFormFilled(@PathVariable Long id,
                                     @ModelAttribute RentalRequestDto rentalRequestDto,
                                     Model model){
        log.info("Receiving CarDto by id={}",id);
        CarDto carDto = carService.getCarById(id);
        model.addAttribute("carDto",carDto);
        log.info("Receiving CarDto received {}",carDto);
        return "rental";
    }

    @PostMapping
    public String makeRental(@Valid @ModelAttribute RentalRequestDto rentalRequestDto,
                             BindingResult result,
                             @ModelAttribute CarDto carDto,
                             Model model){
        if(result.hasErrors()){
            log.debug("Rent request had errors");
            return "rental";
        }
        log.debug("Getting data about logged user");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser)auth.getPrincipal();
        log.debug("Logged {}",user);
        log.debug("Making rental");
        rentalService.makeRental(rentalRequestDto,carDto,user);
        log.debug("Rental made");
        return "redirect:/user/rental";
    }
}

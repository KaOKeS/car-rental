package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.RentalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/management/manager")
public class ManagerController {

    private final RentalService rentalService;

    @RequestMapping("rental")
    public String showAllRentals(Model model,
                                 HttpServletRequest request,
                                 @RequestParam(required = false) String pageNumber,
                                 @RequestParam(required = false) String perPage){

        log.info("If user is Admin or Manager then check how many new rentals exists");
        int newRentals = (request.isUserInRole("MANAGER") || request.isUserInRole("ADMIN")) ? rentalService.getAmountOfNotConfirmedRentals() : 0;
        model.addAttribute("newRentals", newRentals);

        Page<RentalDto> rentalDtos = rentalService.getAllRentals(pageNumber,perPage);
        model.addAttribute("rentalDtos",rentalDtos);
        model.addAttribute("totalItems",rentalDtos.getTotalElements());
        model.addAttribute("totalPages",rentalDtos.getTotalPages());

        return "management_rental";
    }

    @RequestMapping(value = "rental",params = "rentalSelector")
    public String showAllRentalsAccordingToSelector(Model model,
                                                    HttpServletRequest request,
                                                    @RequestParam(required = false) String pageNumber,
                                                    @RequestParam(required = false) String perPage,
                                                    @RequestParam String rentalSelector){

        log.info("If user is Admin or Manager then check how many new rentals exists");
        int newRentals = (request.isUserInRole("MANAGER") || request.isUserInRole("ADMIN")) ? rentalService.getAmountOfNotConfirmedRentals() : 0;
        model.addAttribute("newRentals", newRentals);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser)auth.getPrincipal();
        Page<RentalDto> rentalDtos = rentalService.getAllRequestedRentals(pageNumber,perPage,rentalSelector);
        model.addAttribute("rentalDtos",rentalDtos);
        model.addAttribute("totalItems",rentalDtos.getTotalElements());
        model.addAttribute("totalPages",rentalDtos.getTotalPages());
        model.addAttribute("rentalSelector",rentalSelector);

        return "management_rental";
    }

    @GetMapping(value = "/rental/{id}")
    public String showUserRentalDetails(Model model,
                                        @PathVariable Long id){
        RentalDto rentalDto = rentalService.getRentalById(id);
        model.addAttribute("rentalDto",rentalDto);
        return "management_rental_details";
    }
}

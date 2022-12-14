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

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final RentalService rentalService;

    @GetMapping("/rental")
    public String showUserRentalPage(Model model,
                                     @RequestParam(required = false) String pageNumber,
                                     @RequestParam(required = false) String perPage){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser)auth.getPrincipal();
        Page<RentalDto> rentalDtos = rentalService.getAllRentalsByUserId(user.getId(),pageNumber,perPage);
        model.addAttribute("rentalDtos",rentalDtos);
        model.addAttribute("totalItems",rentalDtos.getTotalElements());
        model.addAttribute("totalPages",rentalDtos.getTotalPages());
        return "user_rental";
    }

    @GetMapping(value = "/rental", params = "rentalSelector")
    public String showUserRentalPageAsRequested(Model model,
                                                @RequestParam("rentalSelector") String rentalSelector,
                                                @RequestParam(required = false) String pageNumber,
                                                @RequestParam(required = false) String perPage){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser)auth.getPrincipal();
        Page<RentalDto> rentalDtos = rentalService.getRequestedRentalsByUserId(user.getId(),pageNumber,perPage,rentalSelector);
        model.addAttribute("rentalDtos",rentalDtos);
        model.addAttribute("totalItems",rentalDtos.getTotalElements());
        model.addAttribute("totalPages",rentalDtos.getTotalPages());
        model.addAttribute("rentalSelector",rentalSelector);
        return "user_rental";
    }

    @GetMapping(value = "/rental/{id}")
    public String showUserRentalDetails(Model model,
                                        @PathVariable Long id){
        RentalDto rentalDto = rentalService.getRentalById(id);
        model.addAttribute("rentalDto",rentalDto);
        return "user_rental_details";
    }

    @GetMapping(value = "/rental/{id}/invoice")
    public String showUserRentalInvoice(Model model,
                                        @PathVariable Long id){
        RentalDto rentalDto = rentalService.getRentalById(id);
        model.addAttribute("rentalDto",rentalDto);
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("date",now.toLocalDate());
        model.addAttribute("time",now.toLocalTime().getHour() + ":" + now.toLocalTime().getMinute());
        return "invoice";
    }
}

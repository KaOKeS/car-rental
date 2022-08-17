package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.exception.UserAlreadyExistException;
import com.dawidpater.project.carrental.service.RentalUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
@Slf4j
public class RegistrationController {
    private final RentalUserService rentalUserService;

    @GetMapping
    public String showRegistrationForm(@ModelAttribute("userDto") RentalUserDto rentalUserDto, Model model){
        return "/register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("userDto") RentalUserDto rentalUserDto, BindingResult result, Model model){
        log.debug("User registration with RentalUserDto = {}",rentalUserDto);
        if (result.hasErrors()) {
            log.debug("Validation failure occured to RentalUserDto = {}",rentalUserDto);
            return "/register";
        }

        log.debug("Registering user account with information: {}", rentalUserDto);
        try {
            rentalUserService.save(rentalUserDto);
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("user", rentalUserDto);
            log.debug("User already exists: {}", rentalUserDto);
            return "redirect:/register?exists";
        }
        log.debug("User registered: {}", rentalUserDto);

        return "redirect:/login?success";
    }
}

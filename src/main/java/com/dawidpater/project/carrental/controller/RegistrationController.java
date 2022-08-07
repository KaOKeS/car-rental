package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.RentalUserConverter;
import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.EmailSenderService;
import com.dawidpater.project.carrental.service.RentalUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.View;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final RentalUserService rentalUserService;
    private final RentalUserConverter rentalUserConverter;
    private final EmailSenderService emailSenderService;

    @GetMapping
    public String showRegistrationForm(@ModelAttribute("userDto") RentalUserDto rentalUserDto, Model model){
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("userDto") RentalUserDto rentalUserDto, Model model){
        RentalUser rentalUser = rentalUserConverter.dtoToEntity(rentalUserDto);
        rentalUserService.save(rentalUser);
        emailSenderService.sendEmail(rentalUser.getEmail(),
                "Registration to Take That Car!",
                "Hi " + rentalUser.getFirstName() +". You have been successfully registered in Take That Car!");
        return "redirect:/login?success";
    }
}

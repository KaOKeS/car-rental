package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.RoleOfUser;
import com.dawidpater.project.carrental.service.RentalUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final RentalUserService rentalUserService;

    @PostMapping("/registeruser")
    public String registerUser(@ModelAttribute("newuser") RentalUser rentalUser){
        rentalUserService.save(rentalUser);
        return "redirect:/login";
    }
}

package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.entity.User;
import com.dawidpater.project.carrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/registeruser")
    public String registerUser(@ModelAttribute("newuser") User user,
                               @RequestParam("birthDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate birthDate){
        System.out.println("I am in RegistrationController before save");
        System.out.println(userService.save(user));
        return "login";
    }
}

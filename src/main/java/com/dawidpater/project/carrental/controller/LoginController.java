package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @PostMapping
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        return "login";
    }
}

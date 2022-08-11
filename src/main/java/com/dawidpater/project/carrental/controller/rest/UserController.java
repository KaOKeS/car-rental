package com.dawidpater.project.carrental.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/rental")
    public String showUserRentalPage(){
        return "user_rental";
    }
}

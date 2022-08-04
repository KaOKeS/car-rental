package com.dawidpater.project.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @PostMapping
    @RequestMapping("/logout")
    public String getLogout(){
        return "redirect:/login";
    }
}

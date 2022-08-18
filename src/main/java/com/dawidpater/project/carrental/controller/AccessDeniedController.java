package com.dawidpater.project.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/accessDenied")
    public String showAccessDeniedPage(Model model){
        model.addAttribute("error","Page you are trying to reach demands more privileges. Access denied.");
        return "error";
    }
}

package com.dawidpater.project.carrental.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    //TODO: Logging?
    @PostMapping
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        return "login";
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        if(errorMessage!=null && errorMessage.equals("User account is locked"))
            errorMessage+=". Please contact with administration.";
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}

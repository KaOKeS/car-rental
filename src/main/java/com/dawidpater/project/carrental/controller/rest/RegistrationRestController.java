package com.dawidpater.project.carrental.controller.rest;

import com.dawidpater.project.carrental.dto.RentalUserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/registration")
public class RegistrationRestController {

    @PostMapping
    public String register(@RequestBody RentalUserDto rentalUserDto){
        return "Does it work?";
    }
}

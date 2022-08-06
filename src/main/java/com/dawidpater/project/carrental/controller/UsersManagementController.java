package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.RentalUserConverter;
import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.RentalUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UsersManagementController {

    private final RentalUserService rentalUserService;
    private final RentalUserConverter rentalUserConverter;

    @GetMapping
    public String manageUserPage(Model model){
        List<RentalUser> allUsers = rentalUserService.getAllUsers();
        List<RentalUserDto> rentalUserDtos = rentalUserConverter.entityToDto(allUsers);
        model.addAttribute("listUsers",rentalUserDtos);
        return "user";
    }
}

package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.RentalUserConverter;
import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.dto.UserRoleDto;
import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.RentalUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/management/admin")
public class UsersManagementController {

    private final RentalUserService rentalUserService;

    @GetMapping("/user")
    public String manageUserPage(Model model){
        List<RentalUserDto> allUsersWithRolesDto = rentalUserService.getAllUsersWithRoles();
        model.addAttribute("listUsers",allUsersWithRolesDto);
        return "user";
    }

    @GetMapping("changeBlockedField/{id}")
    public String blockUser(@PathVariable(value = "id") Long id, Model model){
        rentalUserService.inverseBlockedFieldOfUserById(id);
        return "redirect:/management/admin/user?userBlocked=" + id;
    }

    @GetMapping("makeManager/{id}")
    public String makeManager(@PathVariable(value = "id") Long id, Model model){
        rentalUserService.changeRoleToManager(id);
        return "redirect:/management/admin/user?userBlocked=" + id;
    }

    @GetMapping("makeAdmin/{id}")
    public String makeAdmin(@PathVariable(value = "id") Long id, Model model){
        rentalUserService.changeRoleToAdmin(id);
        return "redirect:/management/admin/user?userBlocked=" + id;
    }

    @GetMapping("makeUser/{id}")
    public String makeUser(@PathVariable(value = "id") Long id, Model model){
        rentalUserService.changeRoleToUser(id);
        return "redirect:/management/admin/user?userBlocked=" + id;
    }

}

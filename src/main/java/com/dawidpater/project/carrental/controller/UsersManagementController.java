package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.converter.RentalUserConverter;
import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.dto.UserRoleDto;
import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.service.RentalUserService;
import com.dawidpater.project.carrental.validator.UserRoleValdation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/management/admin")
public class UsersManagementController {

    private final RentalUserService rentalUserService;

    @GetMapping("/user")
    public String manageUserPage(Model model,
                                 HttpServletRequest request,
                                 @RequestParam(required = false) String pageNumber,
                                 @RequestParam(required = false) String perPage){

        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("User managment feature displayed to user with role {}", Arrays.toString(currentUserRoles.toArray()));

        Page<RentalUserDto> allUsersWithRolesDto = rentalUserService.getAllUsersWithRoles(pageNumber,perPage);

        log.info("All users fetched to display");
        model.addAttribute("users",allUsersWithRolesDto);
        model.addAttribute("totalItems",allUsersWithRolesDto.getTotalElements());
        model.addAttribute("totalPages",allUsersWithRolesDto.getTotalPages());
        return "management_user";
    }

    @GetMapping("changeBlockedField/{id}")
    public String blockUser(@PathVariable(value = "id") Long id, Model model,HttpServletRequest request){
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("User blocking feature displayed to user with role {}", Arrays.toString(currentUserRoles.toArray()));

        rentalUserService.inverseBlockedFieldOfUserById(id);
        log.debug("User with id={} has changed blocked state",id);
        return "redirect:/management/admin/user?userBlocked=" + id;
    }

    @GetMapping("makeManager/{id}")
    public String makeManager(@PathVariable(value = "id") Long id, Model model, HttpServletRequest request){
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("User to MANAGER feature displayed to user with role {}", Arrays.toString(currentUserRoles.toArray()));

        rentalUserService.changeRoleToManager(id);
        log.debug("User with id={} is MANAGER",id);
        return "redirect:/management/admin/user?userHasRoleManager=" + id;
    }

    @GetMapping("makeAdmin/{id}")
    public String makeAdmin(@PathVariable(value = "id") Long id, Model model,HttpServletRequest request){
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("User to ADMIN feature displayed to user with role {}", Arrays.toString(currentUserRoles.toArray()));

        rentalUserService.changeRoleToAdmin(id);
        log.debug("User with id={} is ADMIN",id);
        return "redirect:/management/admin/user?userHasRoleAdmin=" + id;
    }

    @GetMapping("makeUser/{id}")
    public String makeUser(@PathVariable(value = "id") Long id, Model model,HttpServletRequest request){
        List<String> currentUserRoles = UserRoleValdation.getCurrentUserRoles(request);
        log.debug("Change role to USER feature displayed to user with role {}", Arrays.toString(currentUserRoles.toArray()));

        rentalUserService.changeRoleToUser(id);
        log.debug("User with id={} has USER role.",id);
        return "redirect:/management/admin/user?userHasRoleUser=" + id;
    }

}

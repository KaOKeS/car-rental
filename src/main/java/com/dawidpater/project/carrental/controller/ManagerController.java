package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.RejectionRequestDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.constant.PaymentStatus;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalAlreadyStartedException;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalNotConfirmedOrAlreadyRejectedException;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalNotEvenStartedException;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalNotPaidOrNotEndedException;
import com.dawidpater.project.carrental.service.RentalService;
import com.dawidpater.project.carrental.validator.UserRoleValdation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/management/manager")
public class ManagerController {

    private final RentalService rentalService;

    @RequestMapping("rental")
    public String showAllRentals(Model model,
                                 HttpServletRequest request,
                                 @RequestParam(required = false) String pageNumber,
                                 @RequestParam(required = false) String perPage){

        log.info("If user is Admin or Manager then check how many new rentals exists");
        int newRentals = (request.isUserInRole("MANAGER") || request.isUserInRole("ADMIN")) ? rentalService.getAmountOfNotConfirmedAndRejectedRentals() : 0;
        model.addAttribute("newRentals", newRentals);

        Page<RentalDto> rentalDtos = rentalService.getAllRentals(pageNumber,perPage);
        model.addAttribute("rentalDtos",rentalDtos);
        model.addAttribute("totalItems",rentalDtos.getTotalElements());
        model.addAttribute("totalPages",rentalDtos.getTotalPages());

        return "management_rental";
    }

    @RequestMapping(value = "rental",params = "rentalSelector")
    public String showAllRentalsAccordingToSelector(Model model,
                                                    HttpServletRequest request,
                                                    @RequestParam(required = false) String pageNumber,
                                                    @RequestParam(required = false) String perPage,
                                                    @RequestParam String rentalSelector){

        log.info("If user is Admin or Manager then check how many new rentals exists");
        int newRentals = (request.isUserInRole("MANAGER") || request.isUserInRole("ADMIN")) ? rentalService.getAmountOfNotConfirmedAndRejectedRentals() : 0;
        model.addAttribute("newRentals", newRentals);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser)auth.getPrincipal();
        Page<RentalDto> rentalDtos = rentalService.getAllRequestedRentals(pageNumber,perPage,rentalSelector);
        model.addAttribute("rentalDtos",rentalDtos);
        model.addAttribute("totalItems",rentalDtos.getTotalElements());
        model.addAttribute("totalPages",rentalDtos.getTotalPages());
        model.addAttribute("rentalSelector",rentalSelector);

        return "management_rental";
    }

    @GetMapping(value = "/rental/{id}")
    public String showUserRentalDetails(Model model,
                                        @PathVariable Long id){
        RentalDto rentalDto = rentalService.getRentalById(id);
        model.addAttribute("rentalDto",rentalDto);
        return "management_rental_details";
    }

    @GetMapping("rental/changeStarted/{id}")
    public String changeStarted(Model model,
                                @PathVariable(value = "id") Long id){
        try{
            rentalService.inverseStartedFieldById(id);
        }catch (RentalNotConfirmedOrAlreadyRejectedException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }

    @GetMapping("rental/changeEnded/{id}")
    public String changeEnded(Model model,
                              @PathVariable(value = "id") Long id){
        try{
            rentalService.inverseEndedFieldById(id);
        }catch (RentalNotEvenStartedException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }

    @GetMapping("rental/changeClosed/{id}")
    public String changeClosed(Model model,
                               @PathVariable(value = "id") Long id){
        try{
            rentalService.inverseClosedFieldById(id);
        }catch (RentalNotPaidOrNotEndedException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }

    @GetMapping("rental/changeConfirmed/{id}")
    public String changeConfirmed(Model model,
                                  @PathVariable(value = "id") Long id){
        try{
            rentalService.inverseConfirmedFieldById(id);
        }catch (RentalAlreadyStartedException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }

        return "redirect:/management/manager/rental/"+id;
    }

    @GetMapping("/rental/rejection/{id}")
    public String showRejectionForm(Model model,
                                    @PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("rejectionRequest",new RejectionRequestDto());
        model.addAttribute("manager",authentication.getName());
        model.addAttribute("rentalId",id);
        return "reject_reason";
    }

    @PostMapping("/rental/rejection/{id}")
    public String saveRejectionReason(Model model,
                                      @Valid @ModelAttribute RejectionRequestDto rejectionRequest,
                                      BindingResult result,
                                      @PathVariable Long id){
        if(result.hasErrors()){
            log.debug("Validation error for {}",rejectionRequest);
            return "redirect:/management/manager/rental/rejection/"+id;
        }

        try{
            rentalService.rejectRentalAccordingToRequest(rejectionRequest);
        }catch (RentalAlreadyStartedException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }
}

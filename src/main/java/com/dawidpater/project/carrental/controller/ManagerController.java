package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.ReclaimProtocolRequestDto;
import com.dawidpater.project.carrental.dto.webrequest.RejectionRequestDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.exception.rentalStatus.*;
import com.dawidpater.project.carrental.service.ReclaimProtocolRequestService;
import com.dawidpater.project.carrental.service.RentalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/management/manager")
public class ManagerController {

    private final RentalService rentalService;
    private final ReclaimProtocolRequestService reclaimProtocolRequestService;

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
        }catch (RentalNotPaidOrNoReclaimProtocol e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }

    //TODO: Post methods
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

    @GetMapping("/rental/{id}/changeBasicPayment/{paymentStatus}")
    public String changeBasicPayment(Model model,
                                     @PathVariable Long id,
                                     @PathVariable String paymentStatus){
        try{
            rentalService.changeBasicPayment(id,paymentStatus);
        }catch (RentalNotConfirmedOrAlreadyRejectedException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }

    @GetMapping("/rental/{id}/changeDamagePayment/{paymentStatus}")
    public String changeDamagePayment(Model model,
                                     @PathVariable Long id,
                                     @PathVariable String paymentStatus){
        try{
            rentalService.changeDamagePayment(id,paymentStatus);
        }catch (NoReclaimProtocolException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }

    @GetMapping("/rental/reclaimProtocol/{id}")
    public String showReclaimProtocolForm(Model model,
                                          @PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("manager",authentication.getName());
        if(!model.containsAttribute("reclaimProtocol"))
            model.addAttribute("reclaimProtocol", new ReclaimProtocolRequestDto());
        model.addAttribute("rentalId",id);
        return "reclaim_protocol";
    }

    @PostMapping("/rental/reclaimProtocol/")
    public String saveReclaimProtocol(Model model,
                                      @Valid @ModelAttribute ReclaimProtocolRequestDto reclaimProtocol,
                                      BindingResult result){
        if(result.hasErrors()){
            log.debug("Validation error for {}",reclaimProtocol);
            Long id = reclaimProtocol.getRentalId();
            return "redirect:/management/manager/rental/rejection/"+id;
        }

        Long id = reclaimProtocol.getRentalId();
        try{
            rentalService.saveReclaimProtocol(id,reclaimProtocol);
        }catch (RentalNotEndedException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }

        return "redirect:/management/manager/rental/"+id;
    }

    @PostMapping("/rental/deleteReclaimProtocol")
    public String deleteReclaimProtocol(Model model,
                                        @ModelAttribute RentalDto rentalDto){
        Long id = rentalDto.getId();
        try{
            rentalService.deleteReclaimProtocol(id);
        }catch (NoReclaimProtocolException e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }

    @GetMapping("/rental/updateReclaimProtocol/{id}")
    public String updateReclaimProtocol(Model model,
                                        @PathVariable Long id){

        ReclaimProtocolRequestDto reclaimProtocolRequestDto = reclaimProtocolRequestService.getReclaimProtocolFromRental(id);
        model.addAttribute("reclaimProtocol",reclaimProtocolRequestDto);
        return showReclaimProtocolForm(model,id);
    }

    @PostMapping("/rental/changeCarDamagedStatus")
    public String changeCarDamagedStatus(Model model,
                                        @ModelAttribute RentalDto rentalDto){
        Long id = rentalDto.getId();
        try{
            rentalService.changeCarDamageStatus(id);
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return "redirect:/management/manager/rental/"+id;
    }
}

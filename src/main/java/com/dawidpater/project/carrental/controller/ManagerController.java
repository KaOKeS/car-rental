package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.InvoiceDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.ReclaimProtocolRequestDto;
import com.dawidpater.project.carrental.dto.webrequest.RejectionRequestDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.constant.PaymentStatus;
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
import java.math.BigDecimal;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/management/manager")
public class ManagerController {

    private final RentalService rentalService;
    private final ReclaimProtocolRequestService reclaimProtocolRequestService;
    private final String REDIRECT_TO_RENTAL = "redirect:/management/manager/rental/";
    private final String ERROR = "error";

    @GetMapping("rental")
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

    @GetMapping(value = "rental",params = "rentalSelector")
    public String showAllRentalsAccordingToSelector(Model model,
                                                    HttpServletRequest request,
                                                    @RequestParam(required = false) String pageNumber,
                                                    @RequestParam(required = false) String perPage,
                                                    @RequestParam String rentalSelector){

        log.info("If user is Admin or Manager then check how many new rentals exists");
        int newRentals = (request.isUserInRole("MANAGER") || request.isUserInRole("ADMIN")) ? rentalService.getAmountOfNotConfirmedAndRejectedRentals() : 0;
        model.addAttribute("newRentals", newRentals);

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
        model.addAttribute("invoiceDto",rentalDto.getInvoiceDto());
        return "management_rental_details";
    }

    @PostMapping("rental/changeStarted/")
    public String changeStarted(Model model,
                                @ModelAttribute RentalDto rentalDto){
        Long id = rentalDto.getId();
        try{
            rentalService.inverseStartedFieldById(id);
        }catch (RentalNotConfirmedOrAlreadyRejectedException e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
    }

    @PostMapping("rental/changeEnded/")
    public String changeEnded(Model model,
                              @ModelAttribute RentalDto rentalDto){
        Long id = rentalDto.getId();
        try{
            rentalService.inverseEndedFieldById(id);
        }catch (RentalNotEvenStartedException e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
    }

    @PostMapping("rental/changeClosed/")
    public String changeClosed(Model model,
                               @ModelAttribute RentalDto rentalDto){
        Long id = rentalDto.getId();
        try{
            rentalService.inverseClosedFieldById(id);
        }catch (RentalNotPaidOrNoReclaimProtocol e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
    }


    @PostMapping("rental/changeConfirmed/")
    public String changeConfirmed(Model model,
                                  @ModelAttribute RentalDto rentalDto){
        Long id = rentalDto.getId();
        try{
            rentalService.inverseConfirmedFieldById(id);
        }catch (RentalAlreadyStartedException e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }

        return REDIRECT_TO_RENTAL+id;
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
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
    }

    @PostMapping("/rental/rejection/delete")
    public String deleteRejectionOfRental(Model model,
                                          @ModelAttribute RentalDto rentalDto){
            Long id = rentalDto.getId();
            try{
                rentalService.inverseRejectedAndDeleteReason(id);
            }catch (RentalAlreadyStartedException e){
                model.addAttribute(ERROR,e.getMessage());
                return showUserRentalDetails(model,id);
            }

            return REDIRECT_TO_RENTAL+id;
    }

    @PostMapping("/rental/changeBasicPayment/")
    public String changeBasicPayment(Model model,
                                     @ModelAttribute RentalDto rentalDto,
                                     @ModelAttribute InvoiceDto invoiceDto){
        Long id = rentalDto.getId();
        PaymentStatus basicPaymentStatus = invoiceDto.getBasicPaymentStatus();
        try{
            rentalService.changeBasicPayment(id,basicPaymentStatus);
        }catch (RentalNotConfirmedOrAlreadyRejectedException e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
    }

    @PostMapping("/rental/changeDamagePayment/")
    public String changeDamagePayment(Model model,
                                      @ModelAttribute RentalDto rentalDto,
                                      @ModelAttribute InvoiceDto invoiceDto){
        Long id = rentalDto.getId();
        PaymentStatus damagePaymentStatus = invoiceDto.getDamagePaymentStatus();
        try{
            rentalService.changeDamagePayment(id,damagePaymentStatus);
        }catch (NoReclaimProtocolException e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
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
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }

        return REDIRECT_TO_RENTAL+id;
    }

    @PostMapping("/rental/deleteReclaimProtocol")
    public String deleteReclaimProtocol(Model model,
                                        @ModelAttribute RentalDto rentalDto){
        Long id = rentalDto.getId();
        try{
            rentalService.deleteReclaimProtocol(id);
        }catch (NoReclaimProtocolException e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
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
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
    }

    @PostMapping("/rental/setDamageCost")
    public String setDamageCost(Model model,
                                @ModelAttribute RentalDto rentalDto,
                                @ModelAttribute InvoiceDto invoiceDto){
        Long id = rentalDto.getId();
        try{
            rentalService.setDamageCost(id,invoiceDto.getDamageCost());
        }catch (NoReclaimProtocolException e){
            model.addAttribute(ERROR,e.getMessage());
            return showUserRentalDetails(model,id);
        }
        return REDIRECT_TO_RENTAL+id;
    }
}

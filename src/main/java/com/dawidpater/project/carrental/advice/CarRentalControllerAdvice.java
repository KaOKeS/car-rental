package com.dawidpater.project.carrental.advice;

import com.dawidpater.project.carrental.controller.ManagerController;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalStatusException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;


@AllArgsConstructor
@Slf4j
@ControllerAdvice
public class CarRentalControllerAdvice {
    private final String ERROR = "error";
    private final ManagerController managerController;

    @ExceptionHandler(RentalStatusException.class)
    public String showRentalErrorPage(Model model, RentalStatusException e){
        log.debug("Exception of RentalStatus occured with message={} for rental with id={}.\n{}",e.getMessage(),e.getRentalId(),e.getStackTrace());
        model.addAttribute(ERROR,e.getMessage());
        return managerController.showUserRentalDetails(model,e.getRentalId());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String showErrorPageAfterNoSuchElementException(Model model, NoSuchElementException e){
        log.debug("NoSuchElementOccured with message={} and stack trace={}0",e.getMessage(),e.getStackTrace());
        model.addAttribute(ERROR,"No data found for your request. Please try again or in case of constant failure contact with administrator");
        return "error";
    }

    @ExceptionHandler(NullPointerException.class)
    public String showErrorPageAfterNullPointerException(Model model, NullPointerException e){
        log.debug("NoSuchElement occured with message={} and stack trace={}",e.getMessage(),e.getStackTrace());
        model.addAttribute(ERROR,"There was empty data passed in request. Did you filled necessary fields?");
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String showGeneralErrorPage(Model model, RuntimeException e){
        log.debug("RuntimeException occured with message={} and stack trace={}",e.getMessage(),e.getStackTrace());
        model.addAttribute(ERROR,"Unknow error occured. Please try again or contact with administrator");
        return "error";
    }
}

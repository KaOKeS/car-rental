package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.*;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FeedbackDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Invoice;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.exception.CarAlreadyRentedException;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.repository.RentalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CarConverter carConverter;
    private final LocalDateTimeFromStringConverter dateConverter;
    private final RentalConverter rentalConverter;
    private final InvoiceService invoiceService;
    private final CarService carService;

    @Transactional
    public RentalDto makeRental(RentalRequestDto rentalRequestDto, CarDto carDto, RentalUser user) throws CarAlreadyRentedException {
        log.debug("Check if Car with id={} not rented between startDate={} and endDate={}",carDto.getId(), rentalRequestDto.getStartDate(), rentalRequestDto.getEndDate());
        if(carService.isCarRentedInDates(carDto.getId(), rentalRequestDto.getStartDate(), rentalRequestDto.getEndDate()))
            throw new CarAlreadyRentedException();

        log.debug("Car not rented. Creating empty Rental object");
        Rental rental = new Rental();

        log.debug("Setting Car to Rental");
        Car car = carConverter.dtoToEntity(carDto);
        rental.setCar(car);

        log.debug("Setting RentalUser to Rental");
        rental.setRentalUser(user);

        log.debug("Setting Company driver to Rental");
        boolean isDriverPresent = rentalRequestDto.isCompanyDriver();
        rental.setCompanyDriver(isDriverPresent);

        log.debug("Setting Start date to Rental");
        LocalDateTime startDate = dateConverter.getDate(rentalRequestDto.getStartDate(),"00:00");
        rental.setStartDate(startDate);

        log.debug("Setting End date to Rental");
        LocalDateTime endDate = dateConverter.getDate(rentalRequestDto.getEndDate(),"23:59");
        rental.setEndDate(endDate);

        log.debug("Setting Driver license to Rental");
        rental.setDrivingLicense(rentalRequestDto.getDrivingLicense());

        log.debug("Creating new invoice");
        Invoice invoice = new Invoice();
        log.debug("Calculating costs of rental for:\s startDate={}\n endDate={}\n car.price={}\n With driver={}",
                startDate,endDate,car.getPrice(),rentalRequestDto.isCompanyDriver());
        invoice.setRentalValue(invoiceService.calculateCosts(car,startDate,endDate,isDriverPresent,new BigDecimal(20)));
        invoice.setAdditionalCost(BigDecimal.valueOf(0));
        invoice.setStatus("Unpaid");

        log.debug("Setting {} to Rental",invoice);
        rental.setInvoice(invoice);

        log.debug("Saving rental");
        rental = rentalRepository.save(rental);

        log.debug("Setting {} to Invoice",rental);
        invoice.setRental(rental);
        log.debug("Saving {}", invoice);
        invoiceService.saveInvoice(invoice);

        return rentalConverter.entityToDto(rental);
    }

    public Page<RentalDto> getAllRentalsByUserId(Long id, String reqPageNumber, String reqPerPage ) {
        log.debug("Parsing to int pageNumber={} and perPage={}. If null default value will be set",reqPageNumber,reqPerPage);
        int pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        int perPage = IntegerTryParse.parse(reqPerPage,5);
        Page<Rental> rentals = rentalRepository.findAllRentalsByUserId(id, PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.DESC, "id")));
        Page<RentalDto> rentalDtos = rentalConverter.entityToDto(rentals);
        return rentalDtos;
    }

    public Page<RentalDto> getRequestedRentalsByUserId(Long id, String reqPageNumber, String reqPerPage, String rentalSelector) {
        log.debug("Parsing to int pageNumber={} and perPage={}. If null default value will be set",reqPageNumber,reqPerPage);
        int pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        int perPage = IntegerTryParse.parse(reqPerPage,5);
        Page<Rental> rentals;
        if (rentalSelector.equals("incoming")) {
            log.debug("Fetching not started rentals, because of RentalSelector={}",rentalSelector);
            rentals = rentalRepository.findAllNotStartedRentalsByUserId(id,PageRequest.of(pageNumber,perPage, Sort.by(Sort.Direction.DESC, "id")));
        }
        else if (rentalSelector.equals("finished")) {
            log.debug("Fetching ended rentals, because of RentalSelector={}",rentalSelector);
            rentals = rentalRepository.findAllEndedRentalsByUserId(id,PageRequest.of(pageNumber,perPage, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            log.debug("Fetching all rentals, because of RentalSelector={}",rentalSelector);
            rentals = rentalRepository.findAllRentalsByUserId(id,PageRequest.of(pageNumber,perPage, Sort.by(Sort.Direction.DESC, "id")));
        }
        Page<RentalDto> rentalDtos = rentalConverter.entityToDto(rentals);
        log.debug("Returning data");
        return rentalDtos;
    }

    public RentalDto getRentalById(Long id) {
        log.debug("Getting Rental by id={}",id);
        Rental rental = rentalRepository.findById(id).orElseThrow();
        log.debug("Fetched {}",rental);
        RentalDto rentalDto = rentalConverter.entityToDto(rental);
        return rentalDto;
    }
}

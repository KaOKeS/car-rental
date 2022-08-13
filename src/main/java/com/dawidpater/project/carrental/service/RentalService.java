package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.*;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.RejectionRequestDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Invoice;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.constant.PaymentStatus;
import com.dawidpater.project.carrental.exception.CarAlreadyRentedException;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalAlreadyStartedException;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalNotConfirmedOrAlreadyRejectedException;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalNotEvenStartedException;
import com.dawidpater.project.carrental.exception.rentalStatus.RentalNotPaidOrNotEndedException;
import com.dawidpater.project.carrental.repository.RentalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
        invoice.setRentalValue(invoiceService.calculateRentalCosts(car,startDate,endDate));
        BigDecimal driverPrice = new BigDecimal(20);
        log.debug("Calculating additional costs");
        invoice.setAdditionalCost(invoiceService.calculateAdditionalCosts(startDate,endDate,rentalRequestDto.isCompanyDriver(),driverPrice));
        invoice.setBasicPaymentStatus(PaymentStatus.UNPAID);
        invoice.setDamagePaymentStatus(PaymentStatus.OK);

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

    public int getAmountOfNotConfirmedAndRejectedRentals() {
        log.debug("Fetching all not confirmed rentals");
        List<Rental> allNotConfirmedRentals = rentalRepository.findAllByConfirmedAndRejected(false,false);
        return allNotConfirmedRentals.size();
    }
    
    public Page<RentalDto> getAllRentals(String reqPageNumber, String reqPerPage){
        log.debug("Parsing to int pageNumber={} and perPage={}. If null default value will be set",reqPageNumber,reqPerPage);
        int pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        int perPage = IntegerTryParse.parse(reqPerPage,5);
        log.debug("Fetching all rentals");
        Page<Rental> allRentals = rentalRepository.findAll(PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.DESC, "id")));
        return rentalConverter.entityToDto(allRentals);
    }

    public Page<RentalDto> getAllRequestedRentals(String reqPageNumber, String reqPerPage, String rentalSelector) {
        log.debug("Parsing to int pageNumber={} and perPage={}. If null default value will be set",reqPageNumber,reqPerPage);
        int pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        int perPage = IntegerTryParse.parse(reqPerPage,5);
        Page<Rental> allRequestedRentals;
        PageRequest page = PageRequest.of(pageNumber, perPage);
        log.debug("Fetching all data according to rentalSelector={}",rentalSelector);
        if(rentalSelector.equals("new"))
            allRequestedRentals = rentalRepository.findAllByConfirmedAndRejected(false,false,page);
        else if(rentalSelector.equals("confirmed"))
            allRequestedRentals = rentalRepository.findAllByConfirmed(true,page);
        else if(rentalSelector.equals("rejected"))
            allRequestedRentals = rentalRepository.findAllByRejected(true,page);
        else if(rentalSelector.equals("withDamagedCar"))
            allRequestedRentals = rentalRepository.findAllByCarDamaged(true,page);
        else if(rentalSelector.equals("ended"))
            allRequestedRentals = rentalRepository.findAllByEnded(true,page);
        else if(rentalSelector.equals("closed"))
            allRequestedRentals = rentalRepository.findAllByClosed(true,page);
        else
            allRequestedRentals = rentalRepository.findAll(page);

        return rentalConverter.entityToDto(allRequestedRentals);
    }

    public void inverseStartedFieldById (Long id) throws RentalNotConfirmedOrAlreadyRejectedException {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        if(!rental.isConfirmed() || rental.isRejected()){
            log.debug("Rental state not changed to started. Rental.isConfirmed={}  OR  Rental.isRejected={}",rental.isConfirmed(),rental.isRejected());
            throw new RentalNotConfirmedOrAlreadyRejectedException("Rental not confirmed or already rejected!");
        }
        log.debug("Changing rental state to started");
        rental.setStarted(!rental.isStarted());
        log.debug("Saving started Rental");
        rentalRepository.save(rental);
    }

    public void inverseEndedFieldById(Long id) throws RentalNotEvenStartedException {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        if(!rental.isStarted()){
            log.debug("Rental can not be ended, because rental.isStarted()={}",rental.isStarted());
            throw new RentalNotEvenStartedException("Not started rental can not be ended");
        }
        log.debug("Changing state of rental to ended");
        rental.setEnded(!rental.isEnded());
        log.debug("Saving ended Rental");
        rentalRepository.save(rental);
    }

    public void inverseClosedFieldById(Long id) throws RentalNotPaidOrNotEndedException{
        Rental rental = rentalRepository.findById(id).orElseThrow();
        if(!rental.isEnded()
                || rental.getInvoice().getBasicPaymentStatus().equals(PaymentStatus.UNPAID)
                || rental.getInvoice().getDamagePaymentStatus().equals(PaymentStatus.UNPAID)){
            log.debug("Rental not closed, because rental.isEnded={} or rental.getInvoice().getBasicPaymentStatus={}  or rental.getInvoice().getDamagePaymentStatus={}",
                    rental.isEnded(), rental.getInvoice().getBasicPaymentStatus(), rental.getInvoice().getDamagePaymentStatus());
            throw new RentalNotPaidOrNotEndedException("Rental is not ended or invoice not paid");
        }
        log.debug("Setting rental to closed");
        rental.setClosed(!rental.isClosed());
        log.debug("Saving closed rental");
        rentalRepository.save(rental);
    }

    public void inverseConfirmedFieldById(Long id) throws RentalAlreadyStartedException{
        Rental rental = rentalRepository.findById(id).orElseThrow();
        if(rental.isStarted() || rental.isRejected()){
            log.debug("Not possible to confirm rental, because it is already started or rejected");
            throw new RentalAlreadyStartedException("Not possible to confirm rental, because it is already started or rejected");
        }
        rental.setConfirmed(!rental.isConfirmed());
        if(rental.isRejected())
            rental.setRejected(false);
        rentalRepository.save(rental);
    }

    public void inverseRejectedFieldById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        rental.setRejected(!rental.isRejected());
        if(rental.isConfirmed())
            rental.setConfirmed(false);
        rentalRepository.save(rental);
    }

    public void rejectRentalAccordingToRequest(RejectionRequestDto rejectionRequest) throws RentalAlreadyStartedException {
        Rental rental = rentalRepository.findById(rejectionRequest.getRentalId()).orElseThrow();
        if(rental.isStarted()){
            log.debug("Not possible to reject rental, because it is already STARTED");
            throw new RentalAlreadyStartedException("Rental is already started. Can not be rejected");
        }
        log.debug("Rental Rejection True");
        rental.setRejected(true);
        log.debug("Rental Confirmation False");
        rental.setConfirmed(false);
        log.debug("Setting rejection reason");
        rental.setRejectionReason(rejectionRequest.getRejectionReason());
        log.debug("Rejected rental with reason - saving");
        rentalRepository.save(rental);
    }
}

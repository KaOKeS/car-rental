package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.*;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FeedbackDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CarConverter carConverter;
    private final LocalDateTimeFromStringConverter dateConverter;
    private final RentalConverter rentalConverter;
    private final RentalUserConverter rentalUserConverter;

    public void makeRental(RentalRequestDto rentalRequestDto, CarDto carDto, RentalUser user) {
        Rental rental = new Rental();
        rental.setCar(carConverter.dtoToEntity(carDto));
        rental.setRentalUser(user);
        rental.setCompanyDriver(rentalRequestDto.isCompanyDriver());
        rental.setStartDate(dateConverter.getDate(rentalRequestDto.getStartDate(),"00:00"));
        rental.setEndDate(dateConverter.getDate(rentalRequestDto.getEndDate(),"23:59"));
        rental.setDrivingLicense(rentalRequestDto.getDrivingLicense());
        rentalRepository.save(rental);
    }

    public Page<RentalDto> getAllRentalsByUserId(Long id, String reqPageNumber, String reqPerPage ) {
        int pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        int perPage = IntegerTryParse.parse(reqPerPage,5);
        Page<Rental> rentals = rentalRepository.findAllRentalsByUserId(id, PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.DESC, "startDate")));
        Page<RentalDto> rentalDtos = rentalConverter.entityToDto(rentals);
        return rentalDtos;
    }

    public Page<RentalDto> getRequestedRentalsByUserId(Long id, String reqPageNumber, String reqPerPage, String rentalSelector) {
        int pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        int perPage = IntegerTryParse.parse(reqPerPage,5);
        Page<Rental> rentals;
        if (rentalSelector.equals("incoming")) {
            rentals = rentalRepository.findAllNotStartedRentalsByUserId(id,PageRequest.of(pageNumber,perPage));
        }
        else if (rentalSelector.equals("finished")) {
            rentals = rentalRepository.findAllEndedRentalsByUserId(id,PageRequest.of(pageNumber,perPage));
        } else {
            rentals = rentalRepository.findAllRentalsByUserId(id,PageRequest.of(pageNumber,perPage));
        }
        Page<RentalDto> rentalDtos = rentalConverter.entityToDto(rentals);
        return rentalDtos;
    }

    public RentalDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        RentalDto rentalDto = rentalConverter.entityToDto(rental);
        return rentalDto;
    }
}

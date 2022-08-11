package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CarConverter carConverter;
    private final LocalDateTimeFromStringConverter dateConverter;

    public void makeRental(RentalRequestDto rentalRequestDto, CarDto carDto, RentalUser user) {
        Rental rental = new Rental();
        rental.setCar(carConverter.dtoToEntity(carDto));
        rental.setRentalUser(user);
        rental.setCompanyDriver(rentalRequestDto.isCompanyDriver());
        rental.setStartDate(dateConverter.getDate(rentalRequestDto.getStartDate(),"00:00"));
        rental.setEndDate(dateConverter.getDate(rentalRequestDto.getEndDate(),"23:59"));
        rentalRepository.save(rental);
    }
}

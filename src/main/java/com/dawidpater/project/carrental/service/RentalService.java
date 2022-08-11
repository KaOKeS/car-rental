package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    public void makeRental(RentalRequestDto rentalRequestDto, CarDto carDto) {

    }
}

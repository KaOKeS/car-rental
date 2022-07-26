package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.CarRental;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.Review;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }
}

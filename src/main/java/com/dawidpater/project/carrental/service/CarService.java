package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.model.Car;
import com.dawidpater.project.carrental.model.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getTopCars(){
        return carRepository.findAll();
    }

    public List<Car> getCarsWithWebContent(){
        return carRepository.getCarsWithWebContent();
    }
}

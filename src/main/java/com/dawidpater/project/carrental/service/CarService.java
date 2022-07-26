package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCarsWithWebContent(){
        List<Car> topCars = new ArrayList<>();
        topCars.add(carRepository.getBestCarOfClassWithWebContent("family"));
        topCars.add(carRepository.getBestCarOfClassWithWebContent("transport"));
        topCars.add(carRepository.getBestCarOfClassWithWebContent("sport"));
        return topCars;
    }
}

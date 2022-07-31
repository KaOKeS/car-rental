package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getBestCarFromEachType(){
        List<Car> topCars = new ArrayList<>();
        topCars.add(carRepository.getBestCarOfRequestedType("family"));
        topCars.add(carRepository.getBestCarOfRequestedType("transport"));
        topCars.add(carRepository.getBestCarOfRequestedType("sport"));
        return topCars;
    }

    public List<String> getAllCarsTypes(){
        return carRepository.getAllCarTypes();
    }

    public List<Car> getCarsAsRequested(Map<String,String> reqParams){
        String type = (reqParams.get("type")=="0") ? "%" : reqParams.get("type");
        List<Car> carsRentedInThisTime = carRepository.getAllRentedCarsAccordingToRequest("%","%",0,155,LocalDateTime.of(2021,01,01,00,00,00),LocalDateTime.of(2022,07,02,23,25,25));
        List<Car> allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequest("%","%","0","155");
        carsRentedInThisTime.forEach((car -> { allCarsAccordingToRequest.remove(car);}));
        return allCarsAccordingToRequest;
    }
}

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

    public List<Car> getBestCarFromEachType(){
        List<Car> topCars = new ArrayList<>();
        topCars.add(carRepository.getBestCarOfRequestedType("family"));
        topCars.add(carRepository.getBestCarOfRequestedType("transport"));
        topCars.add(carRepository.getBestCarOfRequestedType("sport"));
        return topCars;
    }

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public List<Car> findByType(String type){
        if(type.isEmpty()){
            return carRepository.findAll();
        }
        return carRepository.searchByClass(type);
    }

    public List<String> getAllCarsTypes(){
        return carRepository.getAllCarTypes();
    }
}

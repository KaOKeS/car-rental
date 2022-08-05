package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.entity.Car;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarConverter {
    public Car dtoToEntity(CarDto carDto){
        ModelMapper mapper = new ModelMapper();
        Car car = mapper.map(carDto, Car.class);
        return car;
    }

    public CarDto entityToDto(Car car){
        ModelMapper mapper = new ModelMapper();
        CarDto carDto = mapper.map(car, CarDto.class);
        return carDto;
    }

    public List<Car> dtoToEntity(List<CarDto> carDtos){
        return carDtos.stream().map(carDto -> dtoToEntity(carDto)).collect(Collectors.toList());
    }

    public List<CarDto> entityToDto(List<Car> cars){
        return cars.stream().map(car -> entityToDto(car)).collect(Collectors.toList());
    }
}

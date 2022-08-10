package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CarConverter {
    public Car dtoToEntity(CarDto carDto){
        ModelMapper mapper = new ModelMapper();
        log.info("carDto to convert {}",carDto);
        Car car = mapper.map(carDto, Car.class);
        log.info("Car after conversion {}",car);
        return car;
    }

    public CarDto entityToDto(Car car){
        ModelMapper mapper = new ModelMapper();
        log.info("Car to convert {}",car);
        CarDto carDto = mapper.map(car, CarDto.class);
        log.info("carDto after conversion {}",carDto);
        return carDto;
    }

    public List<Car> dtoToEntity(List<CarDto> carDtos){
        log.info("Converting List<CarDto>");
        return carDtos.stream().map(carDto -> dtoToEntity(carDto)).collect(Collectors.toList());
    }

    public List<CarDto> entityToDto(List<Car> cars){
        log.info("Converting List<Car>");
        return cars.stream().map(car -> entityToDto(car)).collect(Collectors.toList());
    }

    public Page<CarDto> entityToDto(Page<Car> cars){
        log.info("Converting Page<CarDto>");
        Page<CarDto> dtoPage = cars.map(this::entityToDto);
        return dtoPage;
    }


}

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
        log.debug("carDto to convert {}",carDto);
        Car car = mapper.map(carDto, Car.class);
        log.debug("Car after conversion {}",car);
        return car;
    }

    public CarDto entityToDto(Car car){
        ModelMapper mapper = new ModelMapper();
        log.debug("Car to convert {}",car);
        CarDto carDto = mapper.map(car, CarDto.class);
        log.debug("carDto after conversion {}",carDto);
        return carDto;
    }

    public List<Car> dtoToEntity(List<CarDto> carDtos){
        log.debug("Converting List<CarDto>");
        return carDtos.stream().map(this::dtoToEntity).toList();
    }

    public List<CarDto> entityToDto(List<Car> cars){
        log.debug("Converting List<Car>");
        return cars.stream().map(this::entityToDto).toList();
    }

    public Page<CarDto> entityToDto(Page<Car> cars){
        log.debug("Converting Page<CarDto>");
        return cars.map(this::entityToDto);
    }


}

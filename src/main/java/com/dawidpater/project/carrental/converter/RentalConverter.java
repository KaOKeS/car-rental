package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Rental;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalConverter {
    public Rental dtoToEntity(RentalDto rentalDto){
        ModelMapper mapper = new ModelMapper();
        Rental rental = mapper.map(rentalDto, Rental.class);
        return rental;
    }

    public RentalDto entityToDto(Rental rental){
        ModelMapper mapper = new ModelMapper();
        RentalDto rentalDto = mapper.map(rental, RentalDto.class);
        return rentalDto;
    }

    public List<Rental> dtoToEntity(List<RentalDto> rentalDtos){
        return rentalDtos.stream().map(rentalDto -> dtoToEntity(rentalDto)).collect(Collectors.toList());
    }

    public List<RentalDto> entityToDto(List<Rental> rentals){
        return rentals.stream().map(rental -> entityToDto(rental)).collect(Collectors.toList());
    }
}

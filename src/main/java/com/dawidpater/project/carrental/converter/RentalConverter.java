package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Rental;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class RentalConverter {

    private final CarConverter carConverter;
    private final RentalUserConverter rentalUserConverter;
    private final InvoiceConverter invoiceConverter;
    private final FeedbackConverter feedbackConverter;

    public Rental dtoToEntity(RentalDto rentalDto){
        ModelMapper mapper = new ModelMapper();
        log.debug("rentalDto to convert {}",rentalDto);
        Rental rental = mapper.map(rentalDto, Rental.class);
        log.debug("rental after conversion {}",rental);
        return rental;
    }

    public RentalDto entityToDto(Rental rental){
        ModelMapper mapper = new ModelMapper();
        log.debug("rental to convert {}",rental);
        RentalDto rentalDto = mapper.map(rental, RentalDto.class);
        rentalDto.setCarDto(carConverter.entityToDto(rental.getCar()));
        rentalDto.setRentalUserDto(rentalUserConverter.entityToDto(rental.getRentalUser()));
        rentalDto.setInvoiceDto(invoiceConverter.entityToDto(rental.getInvoice()));
        rentalDto.setFeedbackDto(feedbackConverter.entityToDto(rental.getFeedback()));
        log.debug("rentalDto after conversion {}",rentalDto);
        return rentalDto;
    }

    public List<Rental> dtoToEntity(List<RentalDto> rentalDtos){
        log.debug("Converting List<RentalDto>");
        return rentalDtos.stream().map(this::dtoToEntity).toList();
    }

    public List<RentalDto> entityToDto(List<Rental> rentals){
        log.debug("Converting List<Rental>");
        return rentals.stream().map(this::entityToDto).toList();
    }

    public Page<RentalDto> entityToDto(Page<Rental> rentals){
        return rentals.map(this::entityToDto);
    }
}

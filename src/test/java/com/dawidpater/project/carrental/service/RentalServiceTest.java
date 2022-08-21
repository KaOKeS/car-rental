package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.RentalConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.RentalRequestDto;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.exception.CarAlreadyRentedException;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.repository.RentalRepository;
import com.dawidpater.project.carrental.repository.RentalUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql({"classpath:test-schema.sql","classpath:test-data.sql"})
class RentalServiceTest {
    @Autowired
    RentalService rentalService;
    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    CarConverter carConverter;
    @Autowired
    RentalConverter rentalConverter;
    @Autowired
    RentalUserRepository userRepository;

    @Test
    @Transactional
    void makeRental() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");// HH:mm:ss
        LocalDate startRentalDate = LocalDate.now();
        LocalDate endRentalDate = LocalDate.now().plusDays(3);
        String startDate = startRentalDate.format(formatter);
        String endDate = endRentalDate.format(formatter);

        RentalRequestDto rentalRequestDto = RentalRequestDto.builder()
                .companyDriver(false).startDate(startDate).endDate(endDate).build();
        CarDto car = carConverter.entityToDto(carRepository.findById(5l).orElseThrow());
        RentalUser user = userRepository.findById(1l).orElseThrow();
        Page<RentalDto> allRentalsBeforeNewOne = rentalService.getAllRentalsByUserId(1l, "1", "20");
        //when
        rentalService.makeRental(rentalRequestDto,car,user);
        //then
        Page<RentalDto> allRentalsWithNewOne = rentalService.getAllRentalsByUserId(1l, "1", "20");
        assertThat(allRentalsWithNewOne.getContent()).hasSize(allRentalsBeforeNewOne.getContent().size()+1);

        assertThrows(CarAlreadyRentedException.class,() -> rentalService.makeRental(rentalRequestDto,car,user));
    }

    @Test
    @Transactional
    void getAllRentalsByUserId() {
        //given
        Rental rental1 = rentalRepository.findById(2l).orElseThrow();
        Rental rental2 = rentalRepository.findById(6l).orElseThrow();
        Rental rental3 = rentalRepository.findById(7l).orElseThrow();
        Rental rental4 = rentalRepository.findById(8l).orElseThrow();
        Rental rental5 = rentalRepository.findById(9l).orElseThrow();
        Rental rental6 = rentalRepository.findById(10l).orElseThrow();
        Rental rental7 = rentalRepository.findById(11l).orElseThrow();
        Rental rental8 = rentalRepository.findById(12l).orElseThrow();
        Rental rental9 = rentalRepository.findById(13l).orElseThrow();
        List<RentalDto> rentals = rentalConverter.entityToDto(List.of(rental1, rental2, rental3, rental4, rental5, rental6, rental7, rental8, rental9));
        //when
        Page<RentalDto> allRentalsByUserId = rentalService.getAllRentalsByUserId(1L, "1", "20");
        //than
        assertThat(allRentalsByUserId.getContent()).isNotEmpty().hasSize(9).containsExactlyInAnyOrderElementsOf(rentals);
    }

    @Test
    @Transactional
    void getRequestedRentalsByUserId() {
        //given
        RentalDto rental1 = rentalConverter.entityToDto(rentalRepository.findById(2l).orElseThrow());
        RentalDto rental2 = rentalConverter.entityToDto(rentalRepository.findById(6l).orElseThrow());
        RentalDto rental3 = rentalConverter.entityToDto(rentalRepository.findById(7l).orElseThrow());
        RentalDto rental4 = rentalConverter.entityToDto(rentalRepository.findById(8l).orElseThrow());
        RentalDto rental5 = rentalConverter.entityToDto(rentalRepository.findById(9l).orElseThrow());
        RentalDto rental6 = rentalConverter.entityToDto(rentalRepository.findById(10l).orElseThrow());
        RentalDto rental7 = rentalConverter.entityToDto(rentalRepository.findById(11l).orElseThrow());
        RentalDto rental8 = rentalConverter.entityToDto(rentalRepository.findById(12l).orElseThrow());
        RentalDto rental9 = rentalConverter.entityToDto(rentalRepository.findById(13l).orElseThrow());
        List<RentalDto> rentals = List.of(rental1, rental2, rental3, rental4, rental5, rental6, rental7, rental8, rental9);
        //when
        Page<RentalDto> all = rentalService.getRequestedRentalsByUserId(1L, "1", "20", "all");
        Page<RentalDto> incoming = rentalService.getRequestedRentalsByUserId(1L, "1", "20", "incoming");
        Page<RentalDto> finished = rentalService.getRequestedRentalsByUserId(1L, "1", "20", "finished");
        //then
        assertThat(all.getContent()).isNotEmpty().hasSize(9).containsExactlyInAnyOrderElementsOf(rentals);
        assertThat(incoming.getContent()).isNotEmpty().hasSize(6).containsExactlyInAnyOrder(rental4,rental5,rental6,rental7,rental8,rental9);
        assertThat(finished.getContent()).isNotEmpty().hasSize(3).contains(rental1,rental2,rental3);
    }

    @Test
    void getRentalById() {

    }

    @Test
    void getAmountOfNotConfirmedAndRejectedRentals() {
    }

    @Test
    void getAllRentals() {
    }

    @Test
    void getAllRequestedRentals() {
    }

    @Test
    void inverseStartedFieldById() {
    }

    @Test
    void inverseEndedFieldById() {
    }

    @Test
    void inverseClosedFieldById() {
    }

    @Test
    void inverseConfirmedFieldById() {
    }

    @Test
    void inverseRejectedAndDeleteReason() {
    }

    @Test
    void rejectRentalAccordingToRequest() {
    }

    @Test
    void changeBasicPayment() {
    }

    @Test
    void changeDamagePayment() {
    }

    @Test
    void saveReclaimProtocol() {
    }

    @Test
    void deleteReclaimProtocol() {
    }

    @Test
    void changeCarDamageStatus() {
    }

    @Test
    void setDamageCost() {
    }
}
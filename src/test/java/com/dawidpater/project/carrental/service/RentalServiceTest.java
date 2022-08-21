package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.RentalConverter;
import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.repository.RentalRepository;
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
    RentalConverter rentalConverter;
    @Autowired
    RentalUserService userService;

    @Test
    void makeRental() {
    }

    @Test
    @Transactional
    void getAllRentalsByUserId() {
        //given
        Rental rental1 = rentalRepository.findById(2l).orElseThrow();
        Rental rental2 = rentalRepository.findById(6l).orElseThrow();
        Rental rental3 = rentalRepository.findById(7l).orElseThrow();
        Rental rental4 = rentalRepository.findById(8l).orElseThrow();
        Rental rental5 = rentalRepository.findById(8l).orElseThrow();
        Rental rental6 = rentalRepository.findById(10l).orElseThrow();
        Rental rental7 = rentalRepository.findById(11l).orElseThrow();
        Rental rental8 = rentalRepository.findById(12l).orElseThrow();
        Rental rental9 = rentalRepository.findById(13l).orElseThrow();
        List<RentalDto> rentals = rentalConverter.entityToDto(List.of(rental1, rental2, rental3, rental4, rental5, rental6, rental7, rental8, rental9));
        //when
        Page<RentalDto> allRentalsByUserId = rentalService.getAllRentalsByUserId(1L, "1", "20");
        //than
        assertThat(allRentalsByUserId.getContent()).isNotEmpty().hasSize(9).containsAll(rentals);
    }

    @Test
    void getRequestedRentalsByUserId() {
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
package com.dawidpater.project.carrental.integration.service;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.webrequest.FilterCarsRequestDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.repository.RentalRepository;
import com.dawidpater.project.carrental.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarServiceAndCarRepositoryIntegrationTest {
    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarConverter carConverter;
    @Autowired
    private LocalDateTimeFromStringConverter localDateTimeFromStringConverter;
    @Autowired
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository,carConverter,localDateTimeFromStringConverter,rentalRepository);
        Car car1 = Car.builder().brand("Ford").model("Focus").carType("Family").rate(4.5f).price(new BigDecimal(35)).deleted(false).build();
        Car car2 = Car.builder().brand("Ford").model("Mustang").carType("Sport").rate(5.0f).price(new BigDecimal(135)).deleted(false).build();
        Car car3 = Car.builder().brand("Audi").model("RS5").carType("sport").rate(4.9f).price(new BigDecimal(120)).deleted(false).build();
        Car car4 = Car.builder().brand("Ford").model("Mondeo").carType("family").rate(4.4f).price(new BigDecimal(45)).deleted(false).build();
        Car car5 = Car.builder().brand("Peugeot").model("Traveller").carType("Transport").rate(4.3f).price(new BigDecimal(55)).deleted(false).build();
        Car car6 = Car.builder().brand("Renault").model("Trafic").carType("transport").rate(4.35f).price(new BigDecimal(65)).deleted(false).build();
        Car car7 = Car.builder().brand("Ford").model("Mondeo").carType("family").rate(4.45f).price(new BigDecimal(55)).deleted(false).build();
        Car car8 = Car.builder().brand("Ford").model("Mondeo").carType("family").rate(4.3f).price(new BigDecimal(50)).deleted(false).build();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        carRepository.save(car4);
        carRepository.save(car5);
        carRepository.save(car6);
        carRepository.save(car7);
        carRepository.save(car8);
        LocalDateTime now = LocalDate.now().atTime(0, 0);
        Rental rental1 = Rental.builder().startDate(now).endDate(now.plusDays(3)).car(car1).build();
        rentalRepository.save(rental1);
    }

    @Test
    void testIfCarIsRentedInDates() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");// HH:mm:ss

        LocalDateTime startRentalDate1 = LocalDateTime.now();
        LocalDateTime endRentalDate1 = LocalDateTime.now().plusDays(3);
        String startDate1 = startRentalDate1.format(formatter);
        String endDate1 = endRentalDate1.format(formatter);

        LocalDateTime startRentalDate2 = LocalDateTime.now().minusDays(2);
        LocalDateTime endRentalDate2 = LocalDateTime.now().minusDays(1);
        String startDate2 = startRentalDate2.format(formatter);
        String endDate2 = endRentalDate2.format(formatter);
        //when
        boolean isCarRented1 = carService.isCarRentedInDates(1l, startDate1, endDate1);
        boolean isCarRented2 = carService.isCarRentedInDates(1l, startDate2, endDate2);
        //then
        assertThat(isCarRented1).isTrue();
        assertThat(isCarRented2).isFalse();
    }

    @Test
    void testIfGetBestCarFromEachTypeGiveCorrectResult() {
        //given
        CarDto car1 = CarDto.builder().brand("Ford").model("Focus").id(1).carType("Family").rate(4.5f).price(new BigDecimal(35)).deleted(false).build();
        CarDto car2 = CarDto.builder().brand("Ford").model("Mustang").id(2).carType("Sport").rate(5.0f).price(new BigDecimal(135)).deleted(false).build();
        CarDto car6 = CarDto.builder().brand("Renault").model("Trafic").id(6).carType("transport").rate(4.35f).price(new BigDecimal(65)).deleted(false).build();
        //when
        List<CarDto> bestCarFromEachType = carService.getBestCarFromEachType();
        //then
        assertThat(bestCarFromEachType).contains(car1,car2,car6).hasSize(3);
    }

    @Test
    void getCarsOfBrandAndModelAndTypeOrderedByPriceDescNotRentedInSpecifiedDateRangeAndInsidePriceRange() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");// HH:mm:ss
        LocalDateTime startRentalDate = LocalDateTime.now();
        LocalDateTime endRentalDate = LocalDateTime.now().plusDays(3);
        String startDate = startRentalDate.format(formatter);
        String endDate = endRentalDate.format(formatter);

        CarDto car4 = CarDto.builder().brand("Ford").model("Mondeo").id(4).carType("family").rate(4.4f).price(new BigDecimal(45)).deleted(false).build();
        CarDto car7 = CarDto.builder().brand("Ford").model("Mondeo").id(7).carType("family").rate(4.45f).price(new BigDecimal(55)).deleted(false).build();
        CarDto car8 = CarDto.builder().brand("Ford").model("Mondeo").id(8).carType("family").rate(4.3f).price(new BigDecimal(50)).deleted(false).build();


        FilterCarsRequestDto filterCarsRequestDto = FilterCarsRequestDto.builder()
                .type("family").searchBy("Model").search("Mondeo").minPrice("10").maxPrice("100").startDate(startDate).endDate(endDate).orderBy("priceDesc").build();
        //when
        Page<CarDto> carsAsRequested = carService.getCarsAsRequested(filterCarsRequestDto, "1", "10");
        //then
        assertThat(carsAsRequested).isNotEmpty().hasSize(3).containsExactly(car7,car8,car4);
    }

    @Test
    void getCarsOfBrandAndModelAndTypeNotRentedInSpecifiedDateRangeAndInsidePriceRange() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");// HH:mm:ss
        LocalDateTime startRentalDate = LocalDateTime.now();
        LocalDateTime endRentalDate = LocalDateTime.now().plusDays(3);
        String startDate = startRentalDate.format(formatter);
        String endDate = endRentalDate.format(formatter);

        CarDto car4 = CarDto.builder().brand("Ford").model("Mondeo").id(4).carType("family").rate(4.4f).price(new BigDecimal(45)).deleted(false).build();
        CarDto car7 = CarDto.builder().brand("Ford").model("Mondeo").id(7).carType("family").rate(4.45f).price(new BigDecimal(55)).deleted(false).build();
        CarDto car8 = CarDto.builder().brand("Ford").model("Mondeo").id(8).carType("family").rate(4.3f).price(new BigDecimal(50)).deleted(false).build();


        FilterCarsRequestDto filterCarsRequestDto = FilterCarsRequestDto.builder()
                .type("family").searchBy("Model").search("Mondeo").minPrice("10").maxPrice("100").startDate(startDate).endDate(endDate).build();
        //when
        Page<CarDto> carsAsRequested = carService.getCarsAsRequested(filterCarsRequestDto, "1", "10");
        //then
        assertThat(carsAsRequested).isNotEmpty().hasSize(3).contains(car7,car8,car4);
    }

    @Test
    void getCarsNotRentedInSpecifiedDateRangeAndInsidePriceRange() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");// HH:mm:ss
        LocalDateTime startRentalDate = LocalDateTime.now();
        LocalDateTime endRentalDate = LocalDateTime.now().plusDays(3);
        String startDate = startRentalDate.format(formatter);
        String endDate = endRentalDate.format(formatter);

        FilterCarsRequestDto filterCarsRequestDto = FilterCarsRequestDto.builder()
                .type("").searchBy("").search("").minPrice("10").maxPrice("100").startDate(startDate).endDate(endDate).build();
        //when
        Page<CarDto> carsAsRequested = carService.getCarsAsRequested(filterCarsRequestDto, "1", "10");
        //then
        //one car rented, two cars out of limit price
        assertThat(carsAsRequested).isNotEmpty().hasSize(5);
    }
}
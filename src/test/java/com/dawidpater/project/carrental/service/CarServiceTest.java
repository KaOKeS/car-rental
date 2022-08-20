package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.exception.CarNotFoundException;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.repository.RentalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CarServiceTest {

    private CarService carService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarConverter carConverter;
    @Mock
    private LocalDateTimeFromStringConverter localDateTimeFromStringConverter;
    @Mock
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository,carConverter,localDateTimeFromStringConverter,rentalRepository);
    }

    @Test
    void isCarRentedInDates() {
    }

    @Test
    void canAddCar() {
        //given
        CarDto carDto = CarDto.builder().brand("Ford").model("Focus").carType("Family").carEngine("1999cc")
                .hp(145).fuel("petrol").price(new BigDecimal("45")).build();

        Car car = Car.builder().id(1).brand("Ford").model("Focus").carType("Family").carEngine("1999cc")
                .hp(145).fuel("petrol").price(new BigDecimal("45")).build();
        //when
        when(carConverter.dtoToEntity(any(CarDto.class))).thenReturn(car);
        when(carRepository.save(any(Car.class))).thenReturn(car);
        Car savedCar = carService.addCar(carDto);

        //then
        ArgumentCaptor<CarDto> carDtoArgumentCaptor = ArgumentCaptor.forClass(CarDto.class);
        verify(carConverter).dtoToEntity(carDtoArgumentCaptor.capture());
        CarDto capturedCarDto = carDtoArgumentCaptor.getValue();

        assertThat(capturedCarDto).isEqualTo(carDto);

        assertThat(savedCar).isNotNull().isEqualTo(car);
    }

    @Test
    void testGetCarById_CarNotFoundException() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> {
            carService.getCarById(1L);
        });
    }

    @Test
    void testGetCarByIdShouldFindCar() {
        Car car1 = new Car(1L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.8F,"",null);
        Car car2 = new Car(2L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.9F,"",null);
        Car car3 = new Car(3L,"","Ford","Focus","1999cc",250,"petrol","Family", (byte) 4,new BigDecimal(135),false,5.0F,"",null);
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        when(carRepository.findById(2L)).thenReturn(Optional.of(car2));

        assertThat(carRepository.findById(2L)).isNotEmpty()
                .isEqualTo(Optional.of(car2));
    }

    @Test
    void getBestCarFromEachType() {

    }

    @Test
    void getAllCarsTypes() {
        carService.getAllCarsTypes();
        verify(carRepository).getAllCarTypes();
    }

    @Test
    void getAllCars() {
        carService.getAllCars("1","10");
        verify(carRepository).findAllNotDeletedCars(PageRequest.of(0,10));
    }

    @Test
    void getCarsAsRequested() {
    }
}
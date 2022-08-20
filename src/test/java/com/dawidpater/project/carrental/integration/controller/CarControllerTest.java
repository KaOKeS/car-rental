package com.dawidpater.project.carrental.integration.controller;

import com.dawidpater.project.carrental.controller.CarController;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.validator.UserRoleValdation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarControllerTest {

    @Autowired
    CarRepository carRepository;
    @Autowired
    CarService carService;
    @Autowired
    CarController carController;
    @MockBean
    HttpServletRequest httpServletRequest;
    @MockBean
    BindingResult result;
    @MockBean
    RedirectAttributes attributes;


    @Test
    void checkIfCarWillNotBeSaved_bindingResultFailure() {
        //given
        CarDto carDtoToSave = CarDto.builder().brand("Jaguar").model("F-Type").carType("Sport").build();
        Car expectedResultCar = Car.builder().brand("Jaguar").model("F-Type").carType("Sport").id(4).build();
        when(result.hasErrors()).thenReturn(true);
        //when
        carController.saveCar(carDtoToSave,result,null,httpServletRequest,attributes);
        //then
        List<Car> resultCars = carRepository.findAll();
        assertThat(resultCars).doesNotContain(expectedResultCar);
    }

    @Test
    @Order(2)
    void checkIfCarWillBeSaved() {
        //given
        CarDto carDtoToSave = CarDto.builder().brand("Audi").model("RS5").carType("Sport").build();
        Car expectedResultCar = Car.builder().brand("Audi").model("RS5").carType("Sport").id(3).build();
        //when
        carController.saveCar(carDtoToSave,result,null,httpServletRequest,attributes);
        //then
        List<Car> resultCars = carRepository.findAll();
        assertThat(resultCars).contains(expectedResultCar);
    }

    @Test
    @Order(1)
    void checkIfCarByIdWillBeDeleted() throws Exception {
        //given
        Car car1 = Car.builder().brand("Ford").model("Focus").carType("Family").deleted(false).build();
        Car car2 = Car.builder().brand("Ford").model("Mustang").carType("Sport").deleted(false).build();
        carRepository.save(car1);
        carRepository.save(car2);

        when(httpServletRequest.isUserInRole("ADMIN")).thenReturn(true);
        //when
        carController.deleteCar(2l,null,httpServletRequest);
        //then
        assertThat(carService.getCarById(2l).isDeleted()).isTrue();
    }
}
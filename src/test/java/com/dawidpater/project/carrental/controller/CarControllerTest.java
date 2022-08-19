package com.dawidpater.project.carrental.controller;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.service.CarService;
import com.dawidpater.project.carrental.validator.UserRoleValdation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
    @Disabled
    void displayCars() {
    }

    @Test
    @Disabled
    void testDisplayCars() {
    }

    @Test
    @Disabled
    void showNewCarForm() {
    }

    @Test
    @Disabled
    void showUpdateForm() {
    }

    @Test
    void saveCar() {
        //given
        CarDto carDtoToSave = CarDto.builder().brand("Audi").model("RS5").carType("Sport").build();
        Car expectedResultCar = Car.builder().brand("Audi").model("RS5").carType("Sport").id(1).build();
        //when
        carController.saveCar(carDtoToSave,result,null,httpServletRequest,attributes);
        //then
        Car resultCars = carRepository.findById(1l).orElseThrow();
        assertThat(resultCars).isEqualTo(expectedResultCar);
    }

    @Test
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
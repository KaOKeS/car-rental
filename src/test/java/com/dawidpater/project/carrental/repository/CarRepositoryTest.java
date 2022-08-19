package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Rental;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void getTopCarFromSpecifiedType() {
        Car carWithLowerRate = new Car(1L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.8F,"",null);
        Car carWithHigherRate = new Car(2L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.9F,"",null);
        Car carWithHigherRateButDifferentType = new Car(3L,"","Ford","Focus","1999cc",250,"petrol","Family", (byte) 4,new BigDecimal(135),false,5.0F,"",null);
        carRepository.save(carWithLowerRate);
        carRepository.save(carWithHigherRate);
        carRepository.save(carWithHigherRateButDifferentType);
        Car topSportCar = carRepository.findFirstDistinctByCarTypeOrderByRateDesc("Sport");
        assertThat(topSportCar).isEqualTo(carWithHigherRate);
    }

    @Test
    void getAllCarTypes() {
        Car familyCar = new Car(1L,"","Ford","Mustang","3000cc",250,"petrol","Family", (byte) 4,new BigDecimal(135),false,4.8F,"",null);
        Car sportCar = new Car(2L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.9F,"",null);
        Car transportCar = new Car(3L,"","Ford","Focus","1999cc",250,"petrol","Transport", (byte) 4,new BigDecimal(135),false,5.0F,"",null);
        carRepository.save(familyCar);
        carRepository.save(sportCar);
        carRepository.save(transportCar);
        List<String> allCarTypes = carRepository.getAllCarTypes();
        assertThat(allCarTypes)
                .isNotEmpty()
                .hasSize(3)
                .contains("sport","family","transport");
    }

    //TODO: Test more features because this method is complex
    @Test
    void getAllNotRentedCarsAccordingToRequestedDate() {
        Rental rental = new Rental();
        Car familyCar1 = new Car(1L,"","Ford","Focus","3000cc",250,"petrol","Family", (byte) 4,new BigDecimal(135),false,4.8F,"",null);
        rental.setCar(familyCar1);
        rental.setStartDate(LocalDateTime.now());
        rental.setEndDate(LocalDateTime.now().plusDays(6));
        familyCar1.setRental(List.of(rental));
        Car familyCar2 = new Car(2L,"","Ford","Focus","3000cc",250,"petrol","Family", (byte) 4,new BigDecimal(135),false,4.8F,"",null);
        Car sportCar1 = new Car(3L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.9F,"",null);
        Car sportCar2 = new Car(4L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.9F,"",null);
        Car transportCar1 = new Car(5L,"","Ford","Transit","1999cc",250,"petrol","Transport", (byte) 4,new BigDecimal(135),false,5.0F,"",null);
        Car transportCar2 = new Car(6L,"","Ford","Transit","1999cc",250,"petrol","Transport", (byte) 4,new BigDecimal(135),false,5.0F,"",null);

        carRepository.save(familyCar1);
        carRepository.save(familyCar2);
        carRepository.save(sportCar1);
        carRepository.save(sportCar2);
        carRepository.save(transportCar1);
        carRepository.save(transportCar2);

        Page<Car> cars = carRepository.getAllCarsAccordingToRequest(
                "%",
                "%",
                "%",
                new BigDecimal("0"),
                new BigDecimal("1000"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                PageRequest.of(0, 10));

        assertThat(cars.getContent()).isNotEmpty()
                .hasSize(5)
                .contains(familyCar2,sportCar1,sportCar2,transportCar1,transportCar2)
                .doesNotContain(familyCar1);
    }

    @Test
    void findAllNotDeletedCars() {
        Car familyCar1 = new Car(1L,"","Ford","Focus","3000cc",250,"petrol","Family", (byte) 4,new BigDecimal(135),true,4.8F,"",List.of(new Rental()));
        Car familyCar2 = new Car(2L,"","Ford","Focus","3000cc",250,"petrol","Family", (byte) 4,new BigDecimal(135),false,4.8F,"",null);
        Car sportCar1 = new Car(3L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.9F,"",null);
        Car sportCar2 = new Car(4L,"","Ford","Mustang","3000cc",250,"petrol","Sport", (byte) 4,new BigDecimal(135),false,4.9F,"",null);
        Car transportCar1 = new Car(5L,"","Ford","Transit","1999cc",250,"petrol","Transport", (byte) 4,new BigDecimal(135),false,5.0F,"",null);
        Car transportCar2 = new Car(6L,"","Ford","Transit","1999cc",250,"petrol","Transport", (byte) 4,new BigDecimal(135),false,5.0F,"",null);

        carRepository.save(familyCar1);
        carRepository.save(familyCar2);
        carRepository.save(sportCar1);
        carRepository.save(sportCar2);
        carRepository.save(transportCar1);
        carRepository.save(transportCar2);

        Page<Car> cars = carRepository.findAllNotDeletedCars(PageRequest.of(0, 20));

        assertThat(cars.getContent()).isNotEmpty()
                .hasSize(5)
                .contains(sportCar1,sportCar2,transportCar1,transportCar2,familyCar2)
                .doesNotContain(familyCar1);
    }
}
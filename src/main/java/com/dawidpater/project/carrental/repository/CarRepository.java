package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {

    @Query(value = "SELECT * FROM car c WHERE c.car_type=?1 ORDER BY c.rate DESC LIMIT 1",nativeQuery = true)
    Car getBestCarOfRequestedType(String type);

    @Query(value = "SELECT DISTINCT car_type FROM car", nativeQuery = true)
    List<String> getAllCarTypes();

    @Query(value = "SELECT DISTINCT (c.id),c.car_type ,c.brand, c.fuel, c.car_engine, c.hp, c.model, c.sitting_places , c.rent_price, c.deleted, c.rate, c.image_path, c.car_description FROM car c LEFT JOIN rental r ON r.car_id=c.id WHERE brand LIKE ?1 AND car_type LIKE ?2 AND rent_price>=?3 AND rent_price<=?4 AND start_date BETWEEN '2021-12-01 00:00:00' AND '2021-07-02 23:59:59' AND end_date BETWEEN '2021-12-01 00:00:00' AND '2021-07-02 23:59:59'", nativeQuery = true)
    List<Car> getAllRentedCarsAccordingToRequest(String brand, String type, String minPrice, String maxPrice, String startDate, String endDate);

    @Query(value = "SELECT DISTINCT (c.id),c.car_type ,c.brand, c.fuel, c.car_engine, c.hp, c.model, c.sitting_places , c.rent_price, c.deleted, c.rate, c.image_path, c.car_description FROM car c LEFT JOIN rental r ON r.car_id=c.id WHERE brand LIKE ?1 AND car_type LIKE ?2 AND rent_price>=?3 AND rent_price<=?4", nativeQuery = true)
    List<Car> getAllCarsAccordingToRequest(String brand, String type, String minPrice, String maxPrice);
}

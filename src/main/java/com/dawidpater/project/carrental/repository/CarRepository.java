package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {

    String SELECT_NOT_ORDERED_CARS_ACCORDING_TO_REQUEST = "SELECT c FROM Car c " +
            "WHERE LOWER(c.brand) LIKE LOWER(:brand) " +
            "AND LOWER(c.model) LIKE LOWER(:model) " +
            "AND LOWER(c.carType) LIKE LOWER(:carType) " +
            "AND c.price>=:minPrice AND c.price<=:maxPrice " +
            "AND c.deleted=false " +
            "AND c.id NOT IN " +
            "(Select c from Car c LEFT JOIN c.rental r WHERE " +
            ":startDate BETWEEN r.startDate AND r.endDate OR " +
            ":endDate BETWEEN r.startDate AND r.endDate )";

    Car findFirstDistinctByCarTypeOrderByRateDesc(String type);

    @Query(value = "SELECT DISTINCT LOWER(c.carType) FROM Car c")
    List<String> getAllCarTypes();

    @Query(value =  SELECT_NOT_ORDERED_CARS_ACCORDING_TO_REQUEST)
    Page<Car> getAllCarsAccordingToRequest(@Param("brand") String brand,
                                           @Param("model") String model,
                                           @Param("carType") String carType,
                                           @Param("minPrice") BigDecimal minPrice,
                                           @Param("maxPrice") BigDecimal maxPrice,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate,
                                           Pageable page);

    @Query("SELECT c FROM Car c WHERE c.deleted=false")
    Page<Car> findAllNotDeletedCars(Pageable page);
}

package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    final String SELECT_NOT_ORDERED_CARS_ACCORDING_TO_REQUEST = "SELECT * FROM car WHERE LOWER(brand) LIKE LOWER(:brand) " +
                                                                "AND LOWER(model) LIKE LOWER(:model) AND LOWER(car_type) LIKE LOWER(:carType) " +
                                                                "AND rent_price>=:minPrice AND rent_price<=:maxPrice AND id NOT IN " +
                                                                "(SELECT c.id FROM car c LEFT JOIN rental r ON c.id=r.car_id WHERE " +
                                                                "r.start_date BETWEEN :startDate AND :endDate OR" +
                                                                " r.end_date BETWEEN :startDate AND :endDate) " +
                                                                "AND NOT deleted";

    Car findFirstDistinctByCarTypeOrderByRateDesc(String type);

    @Query(value = "SELECT DISTINCT car_type FROM car", nativeQuery = true)
    List<String> getAllCarTypes();

    //Take cars according to request and exclude rented ones(nested query) in one request to DB
    @Query(value =  SELECT_NOT_ORDERED_CARS_ACCORDING_TO_REQUEST, nativeQuery = true)
    Page<Car> getAllCarsAccordingToRequest(@Param("brand") String brand,
                                           @Param("model") String model,
                                           @Param("carType") String carType,
                                           @Param("minPrice") Double minPrice,
                                           @Param("maxPrice") Double maxPrice,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate,
                                           Pageable page);


    List<Car> findByRentalIdIn(List<Long> rentalsIds);

    @Query("SELECT c FROM Car c WHERE c.deleted!=1")
    Page<Car> findAllNotDeletedCars(Pageable page);

    @Query("SELECT DISTINCT c FROM Car c LEFT JOIN c.rental r WHERE c.id=:carId AND " +
            "(r.startDate BETWEEN :startDate AND :endDate OR r.endDate BETWEEN :startDate AND :endDate)")
    Car findIfCarRentedInDates(@Param("carId") Long id,
                               @Param("startDate") LocalDateTime startDate,
                               @Param("endDate") LocalDateTime endDate);
}

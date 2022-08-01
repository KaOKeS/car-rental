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

    @Query(value = "SELECT DISTINCT (c.id),c.car_type ,c.brand, c.fuel, c.car_engine, c.hp, c.model, c.sitting_places , c.rent_price, c.deleted, c.rate, c.image_path, c.car_description FROM car c LEFT JOIN rental r ON r.car_id=c.id WHERE c.brand LIKE :brand AND c.model LIKE :model AND c.car_type LIKE :carType AND c.rent_price>=:minPrice AND c.rent_price<=:maxPrice AND r.start_date BETWEEN :startDate AND :endDate AND r.end_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Car> getAllRentedCarsAccordingToRequest(@Param("brand") String brand,
                                                 @Param("model") String model,
                                                 @Param("carType") String type,
                                                 @Param("minPrice") Integer minPrice,
                                                 @Param("maxPrice") Integer maxPrice,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT c.id,c.car_type ,c.brand, c.fuel, c.car_engine, c.hp, c.model, c.sitting_places , c.rent_price, c.deleted, c.rate, c.image_path, c.car_description FROM car c WHERE c.brand LIKE :brand AND c.model LIKE :model AND c.car_type LIKE :carType AND c.rent_price>=:minPrice AND c.rent_price<=:maxPrice", nativeQuery = true)
    List<Car> getAllCarsAccordingToRequest(@Param("brand") String brand,
                                           @Param("model") String model,
                                           @Param("carType") String carType,
                                           @Param("minPrice") Integer minPrice,
                                           @Param("maxPrice") Integer maxPrice);

    List<Car> findByBrandLikeIgnoreCaseAndModelLikeIgnoreCaseAndCarTypeLikeIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(@Param("brand") String brand,
                                                                                                                                   @Param("model") String model,
                                                                                                                                   @Param("carType") String carType,
                                                                                                                                   @Param("minPrice") Double minPrice,
                                                                                                                                   @Param("maxPrice") Double maxPrice);
}

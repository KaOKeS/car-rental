package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    @Query(value = "SELECT * FROM car c JOIN webcontent wc where c.id=wc.car_id",nativeQuery = true)
    List<Car> getCarsWithWebContent();
}

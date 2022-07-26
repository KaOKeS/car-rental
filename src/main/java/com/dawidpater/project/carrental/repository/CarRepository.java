package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    @Query(value = "SELECT * FROM car c JOIN webcontent wc " +
            "ON c.id=wc.car_id WHERE c.class=?1 " +
            "ORDER BY c.rate DESC LIMIT 1",nativeQuery = true)
    Car getBestCarOfRequestedTypeWithWebContent(String type);
}

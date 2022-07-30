package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    @Query(value = "SELECT * FROM car c WHERE c.class=?1 ORDER BY c.rate DESC LIMIT 1",nativeQuery = true)
    Car getBestCarOfRequestedType(String type);

    @Query(value = "SELECT c FROM Car c WHERE c.type = ?1")
    List<Car> searchByClass(String type);

    @Query(value = "SELECT DISTINCT class FROM takethatcar.car", nativeQuery = true)
    List<String> getAllCarTypes();
}

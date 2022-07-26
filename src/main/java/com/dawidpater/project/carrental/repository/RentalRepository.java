package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.CarRental;
import com.dawidpater.project.carrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<CarRental,Long> {

}

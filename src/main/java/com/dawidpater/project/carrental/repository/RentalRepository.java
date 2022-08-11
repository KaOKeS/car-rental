package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long> {

}

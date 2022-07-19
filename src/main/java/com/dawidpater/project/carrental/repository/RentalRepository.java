package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long> {
    @Query(value = "SELECT * FROM rental r JOIN user_rental ur ON r.id=ur.rental_id JOIN `user` u ON u.id=ur.user_id",nativeQuery = true)
    List<Rental> getRentalsWithUsers();
}

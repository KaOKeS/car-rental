package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long> {

    @Query("SELECT r FROM Rental r JOIN r.car c JOIN r.rentalUser ru LEFT JOIN r.feedback f JOIN r.invoice i WHERE ru.id=:userId")
    Page<Rental> findAllRentalsByUserId(@Param("userId") Long userId, Pageable page);

    @Query("SELECT r FROM Rental r JOIN r.car c JOIN r.rentalUser ru LEFT JOIN r.feedback f JOIN r.invoice i WHERE ru.id=:userId AND r.ended=1")
    Page<Rental> findAllEndedRentalsByUserId(@Param("userId") Long userId, Pageable page);

    @Query("SELECT r FROM Rental r JOIN r.car c JOIN r.rentalUser ru LEFT JOIN r.feedback f JOIN r.invoice i WHERE ru.id=:userId AND r.started=0 AND r.ended=0")
    Page<Rental> findAllNotStartedRentalsByUserId(@Param("userId") Long userId, Pageable page);

    @Override
    @Query("SELECT r FROM Rental r JOIN r.car c JOIN r.rentalUser ru LEFT JOIN r.feedback f JOIN r.invoice i WHERE r.id=:id")
    Optional<Rental> findById(@Param("id") Long id);
}

package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query(value = "SELECT * FROM review " +
            "JOIN rental r ON review.rental_id=r.id " +
            "JOIN user_rental ur ON ur.rental_id=r.id " +
            "JOIN rental_car rc ON rc.rental_id=r.id " +
            "JOIN car c ON rc.car_id=c.id " +
            "JOIN `user` u ON ur.user_id=u.id " +
            "ORDER BY review.id DESC limit 5 "
            ,nativeQuery = true)
    List<Review> getFiveLatestReviewsWithUsersAndCars();
}

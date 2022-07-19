package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query(value = "SELECT * FROM review ORDER BY id DESC limit 5",nativeQuery = true)
    List<Review> getFiveLatestReviews();
}
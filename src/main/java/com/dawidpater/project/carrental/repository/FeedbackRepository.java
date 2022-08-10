package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    @Query(value = "SELECT f,r,c,ur FROM Feedback f JOIN FETCH f.rental r JOIN FETCH r.car c JOIN FETCH r.rentalUser ur ORDER BY f.feedbackDate DESC")
    List<Feedback> getFeedbacksOrderByDateDesc();
}



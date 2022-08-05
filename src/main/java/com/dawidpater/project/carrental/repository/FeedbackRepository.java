package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    @Query(value = "SELECT * FROM feedback " +
            "JOIN rental r ON feedback.rental_id=r.id " +
            "JOIN car c ON r.car_id=c.id " +
            "JOIN `rental_user` u ON r.user_id=u.id " +
            "ORDER BY feedback.`feedback_date` DESC limit ?1",nativeQuery = true)
    List<Feedback> getLatestFeedbacksLimitedTo(int amount);
}



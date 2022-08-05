package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    @Query(value = "SELECT * FROM feedback ORDER BY feedback_date DESC limit ?1",nativeQuery = true)
    List<Feedback> getLatestFeedbacksLimitedTo(int amount);
}



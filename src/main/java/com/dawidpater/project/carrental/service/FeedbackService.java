package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository reviewRepository) {
        this.feedbackRepository = reviewRepository;
    }

    public List<Feedback> getLatestReviewsLimitedTo(int amount){
        return feedbackRepository.getLatestReviewsLimitedTo(amount);
    }

    public Feedback save(){
        Feedback review = new Feedback();
        review.setContent("elo");
        review.setRate(4.5F);
        review.setFeedbackDate(LocalDateTime.now());
        return review;
    }
}

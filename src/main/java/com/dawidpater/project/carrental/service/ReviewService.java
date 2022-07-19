package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.Review;
import com.dawidpater.project.carrental.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    public List<Review> getFiveLatestReviews(){
        return reviewRepository.getFiveLatestReviews();
    }

    public Review save(){
        return reviewRepository.save(new Review("elo",4.5F,LocalDateTime.now(),null));
    }
}
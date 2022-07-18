package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.model.Car;
import com.dawidpater.project.carrental.model.CarRepository;
import com.dawidpater.project.carrental.model.Review;
import com.dawidpater.project.carrental.model.ReviewRepository;
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

    public Review save(){
        return reviewRepository.save(new Review(1L,2L,"elo",4.5F,LocalDateTime.now()));
    }
}

package com.dawidpater.project.carrental.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @OneToOne
    @JoinColumn(name = "id")
    private Review review;
    @OneToMany(mappedBy = "rental", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRental> userRental;

    public Rental() {
    }

    public Rental(LocalDateTime startDate, LocalDateTime endDate, Review review, List<UserRental> userRental) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.review = review;
        this.userRental = userRental;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<UserRental> getUserRental() {
        return userRental;
    }

    public void setUserRental(List<UserRental> userRental) {
        this.userRental = userRental;
    }
}

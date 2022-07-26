package com.dawidpater.project.carrental.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @OneToOne(mappedBy = "rental", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Review review;
    @OneToMany(mappedBy = "rental", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserRental> userRentals;
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CarRental> carRentals;

    public Rental() {
    }

    public Rental(Long id, LocalDateTime startDate, LocalDateTime endDate,
                  Review review, Set<UserRental> userRentals, Set<CarRental> carRentals) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.review = review;
        this.userRentals = userRentals;
        this.carRentals = carRentals;
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

    public Set<UserRental> getUserRentals() {
        return userRentals;
    }

    public void setUserRentals(Set<UserRental> userRentals) {
        this.userRentals = userRentals;
    }

    public Set<CarRental> getCarRentals() {
        return carRentals;
    }

    public void setCarRentals(Set<CarRental> carRentals) {
        this.carRentals = carRentals;
    }
}

package com.dawidpater.project.carrental.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "rate")
    private Float rate;
    @Column(name = "date")
    private LocalDateTime date;
    @OneToOne(mappedBy = "review", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Rental rental;

    public Review() {
    }

    public Review(String content, Float rate, LocalDateTime date, Rental rental) {
        this.content = content;
        this.rate = rate;
        this.date = date;
        this.rental = rental;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
}

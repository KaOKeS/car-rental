package com.dawidpater.project.carrental.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_rental")
public class UserRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    public UserRental() {
    }

    public UserRental(Long id, User user, Rental rental) {
        this.id = id;
        this.user = user;
        this.rental = rental;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
}

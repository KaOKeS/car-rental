package com.dawidpater.project.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "rental_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "zip")
    private String zip;
    @Column(name = "birthdate")
    private String birthDate;
    @Column(name = "phone")
    private String phone;
    @Column(name = "document_id")
    private String documentId;
    @Column(name = "blocked")
    private Boolean blocked;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleOfUser roleOfUser;

    @ToString.Exclude
    @OneToOne(mappedBy = "rentalUser", fetch = FetchType.LAZY)
    private Rental rental;

}
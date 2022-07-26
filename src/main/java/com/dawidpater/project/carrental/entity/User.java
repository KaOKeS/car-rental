package com.dawidpater.project.carrental.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
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
    private LocalDateTime birthDate;
    @Column(name = "phone")
    private String phone;
    @Column(name = "document_id")
    private String documentId;
    @Column(name = "blocked")
    private Boolean blocked;
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserRental> userRental;

    public User() {
    }

    public User(Long id, String username, String email, String firstName,
                String lastName, String country, String city,
                String address, String zip, LocalDateTime birthDate, String phone,
                String documentId, Boolean blocked, Role role, Set<UserRental> userRental) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.birthDate = birthDate;
        this.phone = phone;
        this.documentId = documentId;
        this.blocked = blocked;
        this.role = role;
        this.userRental = userRental;
    }

    public User(String username, String email, String firstName, String lastName,
                String country, String city, String address, String zip,
                LocalDateTime birthDate, String phone, String documentId, Boolean blocked,
                Role role, Set<UserRental> userRental) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.birthDate = birthDate;
        this.phone = phone;
        this.documentId = documentId;
        this.blocked = blocked;
        this.role = role;
        this.userRental = userRental;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<UserRental> getUserRental() {
        return userRental;
    }

    public void setUserRental(Set<UserRental> userRental) {
        this.userRental = userRental;
    }
}

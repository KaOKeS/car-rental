package com.dawidpater.project.carrental.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "car")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public @Data class Car {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name="brand")
    private String brand;
    @Column(name="model")
    private String model;
    @Column(name="car_engine")
    private String carEngine;
    @Column(name="hp")
    private int hp;
    @Column(name="fuel")
    private String fuel;
    @Column(name="car_type")
    private String carType;
    @Column(name="sitting_places")
    private byte sittingPlaces;
    @Column(name="rent_price")
    private double price;
    @Column(name="deleted")
    private boolean deleted;
    @Column(name="rate")
    private float rate;
    @Column(name = "car_description")
    private String carDescription;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rental> rental;

    @Override
    public String toString() {
        return  brand.substring(0,1).toUpperCase() + brand.substring(1) + ' ' +
                model.substring(0,1).toUpperCase() + model.substring(1) + " - " +
                " Engine: " + carEngine +
                " Fuel: " + fuel +
                " Hp:" + hp +
                " Type: " + carType +
                " Sitting Places: " + sittingPlaces;
    }
}

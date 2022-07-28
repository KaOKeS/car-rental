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
    @Column(name="brand")
    private String brand;
    @Column(name="model")
    private String model;
    @Column(name="engine")
    private String engine;
    @Column(name="hp")
    private int hp;
    @Column(name="fuel")
    private String fuel;
    @Column(name="class")
    private String type;
    @Column(name="sitting_places")
    private byte sittingPlaces;
    @Column(name="rent_price")
    private double price;
    @Column(name="deleted")
    private boolean deleted;
    @Column(name="rate")
    private float rate;
    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WebContent webContent;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rental> rental;

    @Override
    public String toString() {
        return  brand.substring(0,1).toUpperCase() + brand.substring(1) + ' ' +
                model.substring(0,1).toUpperCase() + model.substring(1) + " - " +
                " Engine: " + engine +
                " Fuel: " + fuel +
                " Hp:" + hp +
                " Type: " + type +
                " Sitting Places: " + sittingPlaces;
    }
}

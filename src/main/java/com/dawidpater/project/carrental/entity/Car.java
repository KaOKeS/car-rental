package com.dawidpater.project.carrental.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="class")
    private String type;
    @Column(name="brand")
    private String brand;
    @Column(name="fuel")
    private String fuel;
    @Column(name="engine")
    private String engine;
    @Column(name="hp")
    private int hp;
    @Column(name="model")
    private String model;
    @Column(name="sitting_places")
    private byte sittingPlaces;
    @Column(name="rent_price")
    private double price;
    @Column(name="deleted")
    private boolean deleted;
    @Column(name="rate")
    private float rate;
    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY)
    private WebContent webContent;
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private Set<CarRental> carRentals;

    public Car() {
    }

    public Car(long id, String type, String brand, String fuel,
               String engine, int hp, String model, byte sittingPlaces,
               double price, boolean deleted, float rate, WebContent webContent,
               Set<CarRental> carRentals) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.fuel = fuel;
        this.engine = engine;
        this.hp = hp;
        this.model = model;
        this.sittingPlaces = sittingPlaces;
        this.price = price;
        this.deleted = deleted;
        this.rate = rate;
        this.webContent = webContent;
        this.carRentals = carRentals;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public byte getSittingPlaces() {
        return sittingPlaces;
    }

    public void setSittingPlaces(byte sittingPlaces) {
        this.sittingPlaces = sittingPlaces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public WebContent getWebContent() {
        return webContent;
    }

    public void setWebContent(WebContent webContent) {
        this.webContent = webContent;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Set<CarRental> getCarRentals() {
        return carRentals;
    }

    public void setCarRentals(Set<CarRental> carRentals) {
        this.carRentals = carRentals;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}

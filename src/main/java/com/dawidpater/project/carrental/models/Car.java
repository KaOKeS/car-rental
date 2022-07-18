package com.dawidpater.project.carrental.models;

public class Car {
    private long id;
    private String type;
    private String brand;
    private int hp;
    private String model;
    private byte sittingPlaces;
    private double price;
    private boolean deleted;

    public Car() {
    }

    public Car(long id, String type, String brand, int hp, String model, byte sittingPlaces, double price, boolean deleted) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.hp = hp;
        this.model = model;
        this.sittingPlaces = sittingPlaces;
        this.price = price;
        this.deleted = deleted;
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
}

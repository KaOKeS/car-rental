package com.dawidpater.project.carrental.model;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private WebContent webContent;

    public Car() {
    }

    public Car(String type, String brand, int hp, String model, byte sittingPlaces, double price, boolean deleted) {
        this.type = type;
        this.brand = brand;
        this.hp = hp;
        this.model = model;
        this.sittingPlaces = sittingPlaces;
        this.price = price;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", hp=" + hp +
                ", model='" + model + '\'' +
                ", sittingPlaces=" + sittingPlaces +
                ", price=" + price +
                ", deleted=" + deleted +
                ", webContent=" + webContent +
                '}';
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
}

package com.dawidpater.project.carrental.model;

import javax.persistence.*;

@Entity
@Table(name = "webcontent")
public class WebContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "image_path")
    private String imagePath;
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public WebContent(String description, String imagePath, Car car) {
        this.description = description;
        this.imagePath = imagePath;
        this.car = car;
    }

    public WebContent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}

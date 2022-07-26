package com.dawidpater.project.carrental.entity;

import javax.persistence.*;
import java.util.Objects;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    public WebContent(Long id, String description, String imagePath, Car car) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebContent that = (WebContent) o;
        return Objects.equals(id, that.id);
    }
}

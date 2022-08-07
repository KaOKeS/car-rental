package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.entity.Rental;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CarDto {
    private long id;
    private String imagePath = "/images/cars/no-car-image.png";
    private String brand;
    private String model;
    private String carEngine;
    private int hp;
    private String fuel;
    private String carType;
    private byte sittingPlaces;
    private double price;
    private boolean deleted;
    private float rate;
    private String carDescription;
    private List<RentalDto> rentalDtos = null;

    @Override
    public String toString() {
        return brand.substring(0,1).toUpperCase() + brand.substring(1) + ' ' +
                model.substring(0,1).toUpperCase() + model.substring(1) + " - " +
                " Engine: " + carEngine +
                " Fuel: " + fuel +
                " Hp:" + hp +
                " Type: " + carType +
                " Sitting Places: " + sittingPlaces;
    }
}

package com.dawidpater.project.carrental.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"rentalDtos","price"})
public class CarDto {
    private static final String DEFAULT_IMAGE_PATH = "/images/cars/no-car-image.png";

    private long id;
    private String imagePath = DEFAULT_IMAGE_PATH;
    @NotEmpty(message = "Brand should be not empty")
    private String brand;
    @NotEmpty(message = "Model should be not empty")
    private String model;
    @NotEmpty(message = "Car engine should be not empty")
    private String carEngine;
    @NotNull
    @Min(value = 1L, message = "Hp must be greather than 1")
    private int hp;
    @NotEmpty(message = "Fuel should be not empty")
    private String fuel;
    @NotEmpty(message = "Car type should be not empty")
    private String carType;
    @NotNull
    @Min(value = 1L, message = "Sitting places mus be at least 1")
    @Max(value = 100,message = "Maximal ammount of sitting places is 100")
    private byte sittingPlaces;
    @NotNull
    @Min(value = 20L, message = "The value must be greather than 20")
    private BigDecimal price;
    private boolean deleted;
    private float rate;
    private String carDescription;
    private List<RentalDto> rentalDtos;

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

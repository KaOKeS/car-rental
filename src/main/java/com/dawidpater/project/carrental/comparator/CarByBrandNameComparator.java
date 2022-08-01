package com.dawidpater.project.carrental.comparator;

import com.dawidpater.project.carrental.entity.Car;

import java.util.Comparator;

public class CarByBrandNameComparator implements Comparator<Car> {

    @Override
    public int compare(Car o1, Car o2) {
        return o1.getBrand().compareTo(o2.getBrand());
    }

    @Override
    public Comparator<Car> reversed() {
        return Comparator.super.reversed();
    }
}

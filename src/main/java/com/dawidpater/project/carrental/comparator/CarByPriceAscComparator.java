package com.dawidpater.project.carrental.comparator;

import com.dawidpater.project.carrental.entity.Car;

import java.util.Comparator;

public class CarByPriceAscComparator implements Comparator<Car> {
    @Override
    public int compare(Car o1, Car o2) {
        if(o1.getPrice()<o2.getPrice())
            return -1;
        else if(o1.getPrice()==o2.getPrice())
            return 0;
        else
            return 1;
    }

    @Override
    public Comparator<Car> reversed() {
        return Comparator.super.reversed();
    }
}

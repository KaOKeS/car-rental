package com.dawidpater.project.carrental.exception;

public class CarAlreadyRentedException extends RuntimeException{
    public CarAlreadyRentedException(String msg){
        super(msg);
    }

    public CarAlreadyRentedException(){};
}

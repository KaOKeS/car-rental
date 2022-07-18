package com.dawidpater.project.carrental.controllers;

import com.dawidpater.project.carrental.models.Car;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private static final List<Car> cars = new ArrayList<>();

    static {
        cars.add(new Car(1L,"sport","Jaguar",250,"F-Type",(byte)2,120F,false));
        cars.add(new Car(2L,"family","Ford",95,"mondeo",(byte)5,35.55F,false));
        cars.add(new Car(3L,"transport","Peugeot",95,"Trafic",(byte)5,45F,false));
    }

    @GetMapping
    public String getCar(Model model){
        model.addAttribute("cars",cars);
        return "car";
    }
}

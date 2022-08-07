package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.exception.CarNotFoundException;
import com.dawidpater.project.carrental.repository.CarRepository;
import com.dawidpater.project.carrental.validator.ReqParamsValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public void addCar(Car car){
        carRepository.save(car);
    }

    public Car getCarById(Long id){
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException());
    }

    public List<Car> getBestCarFromEachType(){
        List<Car> topCars = new ArrayList<>();
        topCars.add(carRepository.findFirstDistinctByCarTypeOrderByRateDesc("family"));
        topCars.add(carRepository.findFirstDistinctByCarTypeOrderByRateDesc("transport"));
        topCars.add(carRepository.findFirstDistinctByCarTypeOrderByRateDesc("sport"));
        return topCars;
    }

    public List<String> getAllCarsTypes(){
        return carRepository.getAllCarTypes();
    }

    public List<Car> getCarsAsRequested(Map<String,String> reqParams){
        if(reqParams.size()==0){
            return carRepository.findAll();
        }
        ReqParamsValidator reqParamsValidator = new ReqParamsValidator();
        reqParamsValidator.isDateValid(reqParams.get("startDate"),reqParams.get("endDate"));
        reqParamsValidator.isDoubleAndMinMaxRangeValid(reqParams.get("minPrice"),reqParams.get("maxPrice"));

        List<Car> allCarsAccordingToRequest = getAllCarsAccordingToRequestOrderBy(reqParams);

        return allCarsAccordingToRequest;
    }

    private String emptyFieldsReplacer(String field){
        if(field==null || field.equals("0") || field.equals("")){
            return "%";
        }
        return field;
    }

    private String searchBySelector(String searchBy, String searchQuery, boolean brand){
        if(searchBy.equalsIgnoreCase("Model") && !brand && !searchQuery.equals("")){
            return searchQuery  + "%";
        }else if(searchBy.equalsIgnoreCase("Brand") && brand && !searchQuery.equals("")){
            return searchQuery + "%";
        }else{
            return "%";
        }
    }

    private List<Car> getAllCarsAccordingToRequestOrderBy(Map<String,String> reqParams){
        ReqParamsValidator reqParamsValidator = new ReqParamsValidator();
        List<Car> allCarsAccordingToRequest = Collections.EMPTY_LIST;

        LocalDateTimeFromStringConverter dateConverter = new LocalDateTimeFromStringConverter();
        LocalDateTime startDate = dateConverter.getDate(reqParams.get("startDate"),"00:00");
        LocalDateTime endDate = dateConverter.getDate(reqParams.get("endDate"),"23:59");

        String brand = searchBySelector(reqParams.get("searchBy"),reqParams.get("searchQuery"),true);
        String model = searchBySelector(reqParams.get("searchBy"),reqParams.get("searchQuery"),false);

        String type = emptyFieldsReplacer(reqParams.get("type"));
        Double minPrice = Double.parseDouble(reqParams.get("minPrice"));
        Double maxPrice = Double.parseDouble(reqParams.get("maxPrice"));


        String orderBy=(reqParams.get("orderBy").equals("null")) ? null : reqParams.get("orderBy");
        if(reqParamsValidator.isOrderByValid(orderBy)){
            Pattern findFieldAndOrderDirection = Pattern.compile("(.+)(Asc|Desc)",Pattern.CASE_INSENSITIVE);
            Matcher getFieldAndOrderDirection = findFieldAndOrderDirection.matcher(orderBy);
            Sort.Direction direction = null;
            String orderByField = null;
            while (getFieldAndOrderDirection.find()){
                direction = (getFieldAndOrderDirection.group(2).equals("Asc") ? Sort.Direction.ASC : Sort.Direction.DESC);
                orderByField = (getFieldAndOrderDirection.group(1).equals("price") ? "rent_price" : "brand");
            }
            allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequest(brand,model,type,minPrice,maxPrice,startDate,endDate, PageRequest.of(0,10, Sort.by(direction,orderByField)));
        }
        else
            allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequest(brand,model,type,minPrice,maxPrice,startDate,endDate,PageRequest.of(0,10));

        return allCarsAccordingToRequest;
    }
}

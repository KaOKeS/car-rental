package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.exception.WrongDataPassedException;
import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public List<Car> getBestCarFromEachType(){
        List<Car> topCars = new ArrayList<>();
        topCars.add(carRepository.getBestCarOfRequestedType("family"));
        topCars.add(carRepository.getBestCarOfRequestedType("transport"));
        topCars.add(carRepository.getBestCarOfRequestedType("sport"));
        return topCars;
    }

    public List<String> getAllCarsTypes(){
        return carRepository.getAllCarTypes();
    }

    public List<Car> getCarsAsRequested(Map<String,String> reqParams){
        if(reqParams.size()==0){
            return carRepository.findAll();
        }
        LocalDateTimeFromStringConverter dateConverter = new LocalDateTimeFromStringConverter();
        dateFieldsValidation(reqParams.get("startDate"),reqParams.get("endDate"));

        LocalDateTime startDate = dateConverter.getDate(reqParams.get("startDate"),"00:00");
        LocalDateTime endDate = dateConverter.getDate(reqParams.get("endDate"),"23:59");

        String brand = searchBySelector(reqParams.get("searchBy"),reqParams.get("searchQuery"),true);
        String model = searchBySelector(reqParams.get("searchBy"),reqParams.get("searchQuery"),false);

        String type = emptyFieldsReplacer(reqParams.get("type"));
        Double minPrice = Double.parseDouble(reqParams.get("minPrice"));
        Double maxPrice = Double.parseDouble(reqParams.get("maxPrice"));

        List<Car> allCarsAccordingToRequest = Collections.EMPTY_LIST;

        if(reqParams.get("orderBy")!= null && !reqParams.get("orderBy").isEmpty()){
            String orderBy=reqParams.get("orderBy");
            if(orderBy.contains("price")){
                allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequestOrderByPriceAsc(brand,model,type,minPrice,maxPrice,startDate,endDate);
                if(orderBy.equals("priceDesc"))
                    Collections.reverse(allCarsAccordingToRequest);
            }
            else if(orderBy.contains("brand")){
                allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequestOrderByBrandAsc(brand,model,type,minPrice,maxPrice,startDate,endDate);
                if(orderBy.equals("brandDesc"))
                    Collections.reverse(allCarsAccordingToRequest);
            }
        }
        else
            allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequestOrderByPriceAsc(brand,model,type,minPrice,maxPrice,startDate,endDate);

        return allCarsAccordingToRequest;
    }

    private List<Double> priceValidation(String minPrice, String maxPrice, boolean max){
        return Collections.EMPTY_LIST;
    }

    private void dateFieldsValidation(String startDate, String endDate) throws WrongDataPassedException{
        Pattern datePattern = Pattern.compile("\\d\\d-\\d\\d-\\d\\d\\d\\d");
        if(startDate==null || endDate==null || !datePattern.matcher(startDate).find() || !datePattern.matcher(endDate).find())
            throw new WrongDataPassedException();
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
}

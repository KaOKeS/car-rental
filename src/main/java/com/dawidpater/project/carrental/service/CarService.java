package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.comparator.CarByBrandNameComparator;
import com.dawidpater.project.carrental.comparator.CarByPriceAscComparator;
import com.dawidpater.project.carrental.exception.WrongDataPassedException;
import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

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

        List<Car> carsRentedInThisTime = carRepository.getAllRentedCarsAccordingToRequest(brand,model,type,0,155,startDate,endDate);
        List<Car> allCarsAccordingToRequest = carRepository.findByBrandLikeIgnoreCaseAndModelLikeIgnoreCaseAndCarTypeLikeIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(brand,model,type,minPrice,maxPrice);
        carsRentedInThisTime.forEach((allCarsAccordingToRequest::remove));

        if(reqParams.get("sortBy")!= null && !reqParams.get("sortBy").isEmpty()){
            if(reqParams.get("sortBy").equals("priceAsc"))
                allCarsAccordingToRequest = allCarsAccordingToRequest.stream().sorted(((o1, o2) -> new CarByPriceAscComparator().compare(o1,o2))).collect(Collectors.toList());
            else if(reqParams.get("sortBy").equals("priceDesc"))
                allCarsAccordingToRequest = allCarsAccordingToRequest.stream().sorted(((o1, o2) -> new CarByPriceAscComparator().reversed().compare(o1,o2))).collect(Collectors.toList());
            else if(reqParams.get("sortBy").equals("brandAsc"))
                allCarsAccordingToRequest = allCarsAccordingToRequest.stream().sorted(((o1, o2) -> new CarByBrandNameComparator().compare(o1,o2))).collect(Collectors.toList());
            else if(reqParams.get("sortBy").equals("brandDesc"))
                allCarsAccordingToRequest = allCarsAccordingToRequest.stream().sorted(((o1, o2) -> new CarByBrandNameComparator().reversed().compare(o1,o2))).collect(Collectors.toList());
        }

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

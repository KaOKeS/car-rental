package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.CarConverter;
import com.dawidpater.project.carrental.converter.IntegerTryParse;
import com.dawidpater.project.carrental.converter.LocalDateTimeFromStringConverter;
import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.webrequest.FilterCarsRequestDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.exception.CarNotFoundException;
import com.dawidpater.project.carrental.repository.CarRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final CarConverter carConverter;
    private final LocalDateTimeFromStringConverter dateConverter;

    public boolean isCarRentedInDates(Long id, String strStartDate, String strEndDate){
        LocalDateTime startDate = dateConverter.getDate(strStartDate, dateConverter.DAY_START_TIME);
        LocalDateTime endDate = dateConverter.getDate(strEndDate, dateConverter.DAY_END_TIME);
        Car rentedCar = carRepository.findIfCarRentedInDates(id, startDate, endDate);
        return (rentedCar==null) ? false : true;
    }

    public Car addCar(CarDto carDto){
        Car car = carConverter.dtoToEntity(carDto);
        return carRepository.save(car);
    }

    public CarDto getCarById(Long id){
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException());
        return carConverter.entityToDto(car);
    }

    public boolean deleteCarById(Long id){
        CarDto carDto = getCarById(id);
        Car car = carConverter.dtoToEntity(carDto);
        car.setDeleted(true);
        carRepository.save(car);
        return true;
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

    public Page<CarDto> getAllCars(String reqPageNumber, String reqPerPage) {
        Integer pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        Integer perPage = IntegerTryParse.parse(reqPerPage,5);
        Page<Car> allNotDeletedCars = carRepository.findAllNotDeletedCars(PageRequest.of(pageNumber, perPage));
        Page<CarDto> carDtos = carConverter.entityToDto(allNotDeletedCars);
        return carDtos;
    }

    public Page<CarDto> getCarsAsRequested(FilterCarsRequestDto filterCarsRequestDto, String reqPageNumber,String reqPerPage){
        log.debug("Trying to parse perPage={} pageSize{} to int. In case of null defaults value will be taken",reqPageNumber,reqPageNumber);
        Integer pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        Integer perPage = IntegerTryParse.parse(reqPerPage,5);

        log.debug("Fetching data from {} to variables",filterCarsRequestDto);
        String startDate = filterCarsRequestDto.getStartDate();
        String endDate = filterCarsRequestDto.getEndDate();
        String minPrice = filterCarsRequestDto.getMinPrice();
        String maxPrice = filterCarsRequestDto.getMaxPrice();

        String type = filterCarsRequestDto.getType();
        String searchBy = filterCarsRequestDto.getSearchBy();
        String search = filterCarsRequestDto.getSearch();
        String orderBy = filterCarsRequestDto.getOrderBy();

        Page<Car> allCarsAccordingToRequestOrderBy = getAllCarsAccordingToRequestOrderBy(type, searchBy, search, minPrice, maxPrice,
                                                                                    startDate, endDate, orderBy, pageNumber, perPage);
        log.debug("allCarsAccordingToRequestOrderBy received from function.");
        Page<CarDto> allCarsAccordingToRequest = carConverter.entityToDto(allCarsAccordingToRequestOrderBy);

        return allCarsAccordingToRequest;
    }

    private Page<Car> getAllCarsAccordingToRequestOrderBy(String reqType, String reqSearchBy, String reqSearch, String reqMinPrice, String reqMaxPrice,
                                                          String reqStartDate, String reqEndDate, String reqOrderBy, int pageNumber, int perPage){
        Page<Car> allCarsAccordingToRequest;

        LocalDateTimeFromStringConverter dateConverter = new LocalDateTimeFromStringConverter();
        LocalDateTime startDate = dateConverter.getDate(reqStartDate,"00:00");
        LocalDateTime endDate = dateConverter.getDate(reqEndDate,"23:59");

        log.debug("Selection if we should search by brand/model thanks to searchBy parameter. Received string from request searchBy={} search value={}",reqSearchBy,reqSearch);
        String brand = searchBySelector(reqSearchBy,reqSearch,true);
        String model = searchBySelector(reqSearchBy,reqSearch,false);
        log.debug("After selector values which will be fetched to SQL:  brand={}  model={}",brand,model);

        String type = emptyFieldsReplacer(reqType);
        Double minPrice = Double.parseDouble(reqMinPrice);
        Double maxPrice = Double.parseDouble(reqMaxPrice);
        log.debug("After assigning to variables: type={} of car, min={} and maxPrice={} of car.",type,minPrice,maxPrice);


        String orderBy= (reqOrderBy==null) ? null : reqOrderBy;
        if(!(orderBy == null  || orderBy.isEmpty())){
            log.debug("orderBy was not null and not empty. Regex trying to fetch necessary data from orderBy={}",orderBy);
            Pattern findFieldAndOrderDirection = Pattern.compile("(.+)(Asc|Desc)",Pattern.CASE_INSENSITIVE);
            Matcher getFieldAndOrderDirection = findFieldAndOrderDirection.matcher(orderBy);
            Sort.Direction direction = null;
            String orderByField = null;
            while (getFieldAndOrderDirection.find()){
                log.debug("Regex found pattern in data.");
                direction = (getFieldAndOrderDirection.group(2).equals("Asc") ? Sort.Direction.ASC : Sort.Direction.DESC);
                orderByField = (getFieldAndOrderDirection.group(1).equals("price") ? "rent_price" : "brand");
            }
            log.debug("Extracted direction={} and orderByField={} from orderBy={}",direction,orderByField,orderBy);
            allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequest(brand,model,type,minPrice,maxPrice,startDate,endDate, PageRequest.of(pageNumber,perPage, Sort.by(direction,orderByField)));
        }
        else{
            log.debug("orderBy was null or empty. Fetching cars without ORDER BY");
            allCarsAccordingToRequest = carRepository.getAllCarsAccordingToRequest(brand,model,type,minPrice,maxPrice,startDate,endDate,PageRequest.of(pageNumber,perPage));

        }
        log.debug("Returning all cars according to request");
        return allCarsAccordingToRequest;
    }

    private String emptyFieldsReplacer(String field){
        if(field==null || field.equals("0") || field.equals("")){
            return "%";
        }
        return field;
    }

    private String searchBySelector(String searchBy, String searchQuery, boolean brand){
        if(searchBy==null || searchQuery==null)
            return "%";
        if(searchBy.equalsIgnoreCase("Model") && !brand && !searchQuery.equals("")){
            return searchQuery  + "%";
        }else if(searchBy.equalsIgnoreCase("Brand") && brand && !searchQuery.equals("")){
            return searchQuery + "%";
        }else{
            return "%";
        }
    }
}

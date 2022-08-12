package com.dawidpater.project.carrental.dto.webrequest;

import com.dawidpater.project.carrental.contract.ComparableStringDates;
import com.dawidpater.project.carrental.validator.annotation.DateIsNotPast;
import com.dawidpater.project.carrental.validator.annotation.IsPriceRangeOk;
import com.dawidpater.project.carrental.validator.annotation.StartDateLessThanEndDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@StartDateLessThanEndDate
@IsPriceRangeOk
@ToString
public class FilterCarsRequestDto implements ComparableStringDates {
    private String type;
    private String searchBy;
    private String search;
    @NotEmpty(message = "Minimum price cannot be empty.")
    private String minPrice;
    @NotEmpty(message = "Maximum price cannot be empty.")
    private String maxPrice;
    @DateIsNotPast
    @NotEmpty(message = "Start date cannot be empty.")
    private String startDate;
    @DateIsNotPast
    @NotEmpty(message = "End date cannot be empty.")
    private String endDate;
    private String orderBy;
}

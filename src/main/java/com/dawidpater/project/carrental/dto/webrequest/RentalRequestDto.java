package com.dawidpater.project.carrental.dto.webrequest;

import com.dawidpater.project.carrental.contract.ComparableStringDates;
import com.dawidpater.project.carrental.dto.CarDto;

import com.dawidpater.project.carrental.validator.annotation.CompanyDriverOrLicenseRequired;
import com.dawidpater.project.carrental.validator.annotation.DateIsNotPast;
import com.dawidpater.project.carrental.validator.annotation.StartDateLessThanEndDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@Getter
@Setter
@NoArgsConstructor
@StartDateLessThanEndDate
@CompanyDriverOrLicenseRequired
public class RentalRequestDto implements ComparableStringDates {
    @DateIsNotPast
    @NotEmpty(message = "Start date cannot be empty.")
    private String startDate;
    @DateIsNotPast
    @NotEmpty(message = "End date cannot be empty.")
    private String endDate;
    private boolean companyDriver;
    private String drivingLicense;
}

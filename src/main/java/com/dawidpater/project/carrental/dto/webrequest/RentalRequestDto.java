package com.dawidpater.project.carrental.dto.webrequest;

import com.dawidpater.project.carrental.dto.CarDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentalRequestDto {
    private String startDate;
    private String endDate;
    private boolean companyDriver;
    private String drivingLicense;
}

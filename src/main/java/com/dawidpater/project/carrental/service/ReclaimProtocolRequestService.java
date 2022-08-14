package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.dto.RentalDto;
import com.dawidpater.project.carrental.dto.webrequest.ReclaimProtocolRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ReclaimProtocolRequestService {
    private final RentalService rentalService;

    public ReclaimProtocolRequestDto getReclaimProtocolFromRental(Long id){
        RentalDto rental = rentalService.getRentalById(id);
        ReclaimProtocolRequestDto reclaimProtocolRequestDto = new ReclaimProtocolRequestDto();
        reclaimProtocolRequestDto.setProtocol(rental.getReclaimProtocol());
        return reclaimProtocolRequestDto;
    }
}

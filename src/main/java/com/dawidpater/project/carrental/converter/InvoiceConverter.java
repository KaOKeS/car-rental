package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.InvoiceDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InvoiceConverter {
    public Invoice dtoToEntity(InvoiceDto invoiceDto){
        ModelMapper mapper = new ModelMapper();
        log.info("InvoiceDto to convert {}",invoiceDto);
        Invoice invoice = mapper.map(invoiceDto, Invoice.class);
        log.info("Invoice after conversion {}",invoice);
        return invoice;
    }

    public InvoiceDto entityToDto(Invoice invoice){
        ModelMapper mapper = new ModelMapper();
        log.info("Invoice to convert {}",invoice);
        InvoiceDto invoiceDto = mapper.map(invoice, InvoiceDto.class);
        log.info("InvoiceDto after conversion {}",invoiceDto);
        return invoiceDto;
    }

    public List<Invoice> dtoToEntity(List<InvoiceDto> invoiceDtos){
        log.info("Converting List<CarDto>");
        return invoiceDtos.stream().map(invoiceDto -> dtoToEntity(invoiceDto)).collect(Collectors.toList());
    }

    public List<InvoiceDto> entityToDto(List<Invoice> invoices){
        log.info("Converting List<Car>");
        return invoices.stream().map(invoice -> entityToDto(invoice)).collect(Collectors.toList());
    }

}

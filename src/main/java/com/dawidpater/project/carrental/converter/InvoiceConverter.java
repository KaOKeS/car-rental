package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.InvoiceDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InvoiceConverter {
    public Invoice dtoToEntity(InvoiceDto invoiceDto){
        if(invoiceDto==null)
            return null;
        ModelMapper mapper = new ModelMapper();
        log.debug("InvoiceDto to convert {}",invoiceDto);
        Invoice invoice = mapper.map(invoiceDto, Invoice.class);
        log.debug("Invoice after conversion {}",invoice);
        return invoice;
    }

    public InvoiceDto entityToDto(Invoice invoice){
        if(invoice==null)
            return null;
        ModelMapper mapper = new ModelMapper();
        log.debug("Invoice to convert {}",invoice);
        InvoiceDto invoiceDto = mapper.map(invoice, InvoiceDto.class);
        log.debug("InvoiceDto after conversion {}",invoiceDto);
        return invoiceDto;
    }

    public List<Invoice> dtoToEntity(List<InvoiceDto> invoiceDtos){
        if(invoiceDtos==null)
            return Collections.emptyList();
        log.debug("Converting List<InvoiceDto>");
        return invoiceDtos.stream().map(this::dtoToEntity).toList();
    }

    public List<InvoiceDto> entityToDto(List<Invoice> invoices){
        if(invoices==null)
            return Collections.emptyList();
        log.debug("Converting List<Invoice>");
        return invoices.stream().map(this::entityToDto).toList();
    }

}

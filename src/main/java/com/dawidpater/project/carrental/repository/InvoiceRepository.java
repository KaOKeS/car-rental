package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}

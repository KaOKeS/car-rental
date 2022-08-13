package com.dawidpater.project.carrental.entity;

import com.dawidpater.project.carrental.entity.constant.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Invoice {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="rental_value")
    private BigDecimal rentalValue;
    @Column(name="additional_cost")
    private BigDecimal additionalCost;
    @Column(name="damage_cost")
    private BigDecimal damageCost;
    @Column(name="basic_payment_status")
    private PaymentStatus basicPaymentStatus;
    @Column(name="damage_payment_status")
    private PaymentStatus damagePaymentStatus;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;
}

package com.dawidpater.project.carrental.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "company_driver")
    private boolean companyDriver;
    @Column(name = "rejected")
    private boolean rejected;
    @Column(name = "rejection_reason")
    private String rejectionReason;
    @Column(name = "confirmed")
    private boolean confirmed;
    @Column(name = "started")
    private boolean started;
    @Column(name = "ended")
    private boolean ended;
    @Column(name = "car_damaged")
    private boolean carDamaged;
    @Column(name = "reclaim_protocol")
    private String reclaimProtocol;
    @Column(name = "driving_license")
    private String drivingLicense;
    @Column(name = "closed")
    private boolean closed;

    @ToString.Exclude
    @OneToOne(mappedBy = "rental", fetch = FetchType.LAZY)
    private Feedback feedback;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private RentalUser rentalUser;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ToString.Exclude
    @OneToOne(mappedBy = "rental", fetch = FetchType.LAZY)
    private Invoice invoice;
}

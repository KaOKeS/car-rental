package com.dawidpater.project.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role_of_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleOfUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "role_of_user")
    private String role;
    @OneToOne(mappedBy = "roleOfUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RentalUser rentalUser;
}

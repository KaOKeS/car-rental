package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.RoleOfUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalUserRepository extends JpaRepository<RentalUser, Long> {
}

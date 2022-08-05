package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.RentalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalUserRepository extends JpaRepository<RentalUser, Long> {
    Optional<RentalUser> findByUsername(String username);

    List<RentalUser> findByRentalIdIn(List<Long> rentalsIds);
}

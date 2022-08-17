package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.RentalUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalUserRepository extends JpaRepository<RentalUser, Long> {
    Optional<RentalUser> findByUsername(String username);

    @Query("SELECT ru FROM RentalUser ru JOIN ru.userRole role")
    Page<RentalUser> getAllUsersWithTheirRole(Pageable page);

    Optional<RentalUser> findByUsernameOrEmail(String username,String email);
}

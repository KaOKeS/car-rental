package com.dawidpater.project.carrental.repository;

import com.dawidpater.project.carrental.entity.RentalUser;
import org.h2.engine.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalUserRepository extends JpaRepository<RentalUser, Long> {
    Optional<RentalUser> findByUsername(String username);

    @Query("SELECT ru FROM RentalUser ru JOIN FETCH ru.userRole role")
    List<RentalUser> getAllUsersWithTheirRole();

    Optional<RentalUser> findByUsernameOrEmail(String username,String email);
}

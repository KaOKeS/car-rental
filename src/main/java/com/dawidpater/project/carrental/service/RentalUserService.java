package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.RoleOfUser;
import com.dawidpater.project.carrental.repository.RentalUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalUserService {
    private final RentalUserRepository rentalUserRepository;

    public RentalUser save(RentalUser rentalUser){
        rentalUser.setRoleOfUser(new RoleOfUser(1L,"user",null));
        rentalUser.setBlocked(false);
        return rentalUserRepository.save(rentalUser);
    }
}

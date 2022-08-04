package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.RoleOfUser;
import com.dawidpater.project.carrental.repository.RentalUserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalUserService implements UserDetailsService {
    private final RentalUserRepository rentalUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return rentalUserRepository.findByUsername(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException(String.format("Username %s not found",username)));
    }

    public RentalUser save(RentalUser rentalUser){
        rentalUser.setRoleOfUser(new RoleOfUser(1L,"user",null));
        rentalUser.setBlocked(false);
        return rentalUserRepository.save(rentalUser);
    }

}

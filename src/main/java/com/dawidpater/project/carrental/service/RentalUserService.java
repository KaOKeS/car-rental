package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.UserRole;
import com.dawidpater.project.carrental.repository.RentalUserRepository;
import com.dawidpater.project.carrental.repository.UserRoleRepository;
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
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RentalUser user = rentalUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found",username)));
        Long userRoleId = user.getUserRole().getId();
        UserRole userRole = userRoleRepository.findById(userRoleId).orElseThrow();
        //We need to add prefix and to it upper case to suit spring security roles
        userRole.setRole("ROLE_" + userRole.getRole().toUpperCase());
        user.setUserRole(userRole);
        return user;
    }

    // TODO: just for test method
    public RentalUser save(RentalUser rentalUser){
        rentalUser.setUserPassword(passwordEncoder.encode(rentalUser.getUserPassword()));
        rentalUser.setUserRole(new UserRole(1L,"user",null));
        rentalUser.setBlocked(false);
        return rentalUserRepository.save(rentalUser);
    }

}

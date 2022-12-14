package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.converter.IntegerTryParse;
import com.dawidpater.project.carrental.converter.RentalUserConverter;
import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.dto.UserRoleDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import com.dawidpater.project.carrental.entity.UserRole;
import com.dawidpater.project.carrental.exception.UserAlreadyExistException;
import com.dawidpater.project.carrental.repository.RentalUserRepository;
import com.dawidpater.project.carrental.repository.UserRoleRepository;
import com.dawidpater.project.carrental.contract.NotyficationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalUserService implements UserDetailsService {
    private final RentalUserRepository rentalUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final RentalUserConverter rentalUserConverter;
    private final NotyficationSender notyficationSenderService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        RentalUser user = rentalUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found",username)));
        Long userRoleId = user.getUserRole().getId();
        UserRole userRole = userRoleRepository.findById(userRoleId).orElseThrow();
        //We need to add prefix and to it upper case to suit spring security roles
        userRole.setRole("ROLE_" + userRole.getRole().toUpperCase());
        user.setUserRole(userRole);
        return user;
    }

    public RentalUser save(RentalUserDto rentalUserDto) throws UserAlreadyExistException {
        log.debug("Before saving user. Checking if already exists. Username={}  email={}",rentalUserDto.getUsername(),rentalUserDto.getEmail());
        rentalUserRepository.findByUsernameOrEmail(rentalUserDto.getUsername(),rentalUserDto.getEmail())
                .ifPresent(user -> {throw new UserAlreadyExistException();});
        log.debug("Building rentalUser from rentalUserDto={}",rentalUserDto);
        RentalUser rentalUser = rentalUserConverter.dtoToEntity(rentalUserDto);
        log.debug("Encoding user password");
        rentalUser.setUserPassword(passwordEncoder.encode(rentalUser.getUserPassword()));
        rentalUser.setUserRole(new UserRole(1L,"user",null));
        rentalUser.setBlocked(false);
        log.debug("Saving user to database");
        rentalUserRepository.save(rentalUser);

        notyficationSenderService.send(rentalUserDto.getEmail(),
        "Registration to Take That Car!",
        "Hi " + rentalUserDto.getFirstName() +". You have been successfully registered in Take That Car!");

        return rentalUser;
    }

    public Page<RentalUserDto> getAllUsersWithRoles(String reqPageNumber, String reqPerPage){
        log.debug("Parsing to int pageNumber={} and perPage={}. If null default value will be set",reqPageNumber,reqPerPage);
        int pageNumber = IntegerTryParse.parse(reqPageNumber,1)-1;
        int perPage = IntegerTryParse.parse(reqPerPage,5);

        Page<RentalUser> allUsers = rentalUserRepository.getAllUsersWithTheirRole(PageRequest.of(pageNumber,perPage, Sort.by(Sort.Direction.ASC,"id")));
        return rentalUserConverter.entityToDto(allUsers);
    }

    public void inverseBlockedFieldOfUserById(Long id) {
        RentalUser rentalUser = rentalUserRepository.findById(id).orElseThrow();
        rentalUser.setBlocked(!rentalUser.getBlocked());
        rentalUserRepository.save(rentalUser);
    }

    public void changeRoleToManager(Long id) {
        RentalUser rentalUser = rentalUserRepository.findById(id).orElseThrow();
        UserRole manager = userRoleRepository.findByRole("MANAGER");
        rentalUser.setUserRole(manager);
        rentalUserRepository.save(rentalUser);
    }

    public void changeRoleToAdmin(Long id) {
        RentalUser rentalUser = rentalUserRepository.findById(id).orElseThrow();
        UserRole admin = userRoleRepository.findByRole("ADMIN");
        rentalUser.setUserRole(admin);
        rentalUserRepository.save(rentalUser);
    }

    public void changeRoleToUser(Long id) {
        RentalUser rentalUser = rentalUserRepository.findById(id).orElseThrow();
        UserRole admin = userRoleRepository.findByRole("USER");
        rentalUser.setUserRole(admin);
        rentalUserRepository.save(rentalUser);
    }
}

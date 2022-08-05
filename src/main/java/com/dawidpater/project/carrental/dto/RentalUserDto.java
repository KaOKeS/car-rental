package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.entity.Rental;
import com.dawidpater.project.carrental.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RentalUserDto {
    private Long id;
    private String username;
    private String userPassword;
    private String email;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String address;
    private String zip;
    private String birthDate;
    private String phone;
    private String documentId;
    private Boolean blocked = false;
    private UserRoleDto userRoleDto = null;
    private List<RentalDto> rentalDtos = null;
}

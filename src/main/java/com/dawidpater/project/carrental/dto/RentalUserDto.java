package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.validator.annotation.PasswordMatches;
import com.dawidpater.project.carrental.validator.annotation.ValidEmail;
import com.dawidpater.project.carrental.validator.annotation.ValidStrongPassword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatches
@ToString
public class RentalUserDto {
    private Long id;
    @NotEmpty(message = "Username cannot be empty!")
    private String username;
    @ValidStrongPassword
    @NotEmpty(message = "Password cannot be empty!")
    private String userPassword;
    private String matchingPassword;
    @ValidEmail
    @NotEmpty(message = "Mail can not be empty")
    private String email;
    @NotEmpty(message = "First name cannot be empty!")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty!")
    private String lastName;
    @NotEmpty(message = "Country cannot be empty!")
    private String country;
    @NotEmpty(message = "City cannot be empty!")
    private String city;
    @NotEmpty(message = "Address cannot be empty!")
    private String address;
    @NotEmpty(message = "Zip cannot be empty!")
    private String zip;
    @NotEmpty(message = "Birth date cannot be empty!")
    private String birthDate;
    @NotEmpty(message = "Phone cannot be empty!")
    private String phone;
    @NotEmpty(message = "Document Id cannot be empty!")
    private String documentId;
    private Boolean blocked;
    private UserRoleDto userRoleDto;
    private List<RentalDto> rentalDtos;
}

package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.entity.RentalUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleDto {
    private Long id;
    private String role;
    private List<RentalUserDto> rentalUserDtos = null;
}

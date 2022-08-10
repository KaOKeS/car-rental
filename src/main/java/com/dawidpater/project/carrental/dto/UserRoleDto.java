package com.dawidpater.project.carrental.dto;

import com.dawidpater.project.carrental.entity.RentalUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRoleDto {
    private Long id;
    private String role;
    private List<RentalUserDto> rentalUserDtos;
}

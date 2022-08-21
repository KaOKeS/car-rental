package com.dawidpater.project.carrental.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(exclude = "rentalUserDtos")
public class UserRoleDto {
    private Long id;
    private String role;
    private List<RentalUserDto> rentalUserDtos;
}

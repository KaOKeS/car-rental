package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalUserConverter {
    public RentalUser dtoToEntity(RentalUserDto userDto){
        ModelMapper mapper = new ModelMapper();
        RentalUser user = mapper.map(userDto, RentalUser.class);
        return user;
    }

    public RentalUserDto entityToDto(RentalUser user){
        ModelMapper mapper = new ModelMapper();
        RentalUserDto userDto = mapper.map(user, RentalUserDto.class);
        return userDto;
    }

    public List<RentalUser> dtoToEntity(List<RentalUserDto> userDtos){
        return userDtos.stream().map(userDto -> dtoToEntity(userDto)).collect(Collectors.toList());
    }

    public List<RentalUserDto> entityToDto(List<RentalUser> users){
        return users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
    }
}

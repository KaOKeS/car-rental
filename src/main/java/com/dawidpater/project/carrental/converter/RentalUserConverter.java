package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.RentalUserDto;
import com.dawidpater.project.carrental.entity.RentalUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RentalUserConverter {
    public RentalUser dtoToEntity(RentalUserDto userDto){
        ModelMapper mapper = new ModelMapper();
        log.debug("RentalUserDto to convert {}",userDto);
        RentalUser user = mapper.map(userDto, RentalUser.class);
        log.debug("RentalUser after conversion {}",user);
        return user;
    }

    public RentalUserDto entityToDto(RentalUser user){
        ModelMapper mapper = new ModelMapper();
        log.debug("RentalUser to convert {}",user);
        RentalUserDto userDto = mapper.map(user, RentalUserDto.class);
        log.debug("RentalUserDto after conversion {}",userDto);
        return userDto;
    }

    public List<RentalUser> dtoToEntity(List<RentalUserDto> userDtos){
        log.debug("Converting List<RentalUserDto>");
        return userDtos.stream().map(userDto -> dtoToEntity(userDto)).collect(Collectors.toList());
    }

    public List<RentalUserDto> entityToDto(List<RentalUser> users){
        log.debug("Converting List<RentalUser>");
        return users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
    }
}

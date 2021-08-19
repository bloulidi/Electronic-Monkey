package com.stackroute.userservice.converter;

import com.stackroute.userservice.dto.UserDto;
import com.stackroute.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public UserDto entityToDto(User user) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDto.class);
    }

    public List<UserDto> entityToDto(List<User> user) {
        return user.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public User dtoToEntity(UserDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, User.class);
    }

    public List<User> dtoToEntity(List<UserDto> dto) {
        return dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }
}
package com.extensivelyscrum.backend.dtoMappers;

import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.security.SecurityConfiguration;

import java.util.ArrayList;

public class UserDtoMapper {
    public static User createUserDtoMapper(CreateUserDto createUserDto) {
        User user = new User();
        user.setFullName(createUserDto.getFullName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(SecurityConfiguration.passwordEncoder().encode(createUserDto.getPassword()));
        return user;
    }
}

package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.dtoMappers.UserDtoMapper;
import com.extensivelyscrum.backend.exception.InvalidEmailFormatException;
import com.extensivelyscrum.backend.exception.UserAlreadyExistsException;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public  UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserWithEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException()
        );
    }

    public void deleteUserWithEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public User createUser(CreateUserDto createUserDto) {
        if(userRepository.existsByEmail(createUserDto.email())) throw new UserAlreadyExistsException("User exists Already!");
        if(!EmailValidator.getInstance().isValid(createUserDto.email())) throw new InvalidEmailFormatException("Invalid Email!");
        User user = UserDtoMapper.createUserDtoMapper(createUserDto);
        return userRepository.save(user);
    }

}

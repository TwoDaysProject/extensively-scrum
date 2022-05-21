package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.repository.UserRepository;
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

}

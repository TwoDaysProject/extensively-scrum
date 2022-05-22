package com.extensivelyscrum.backend.controllerAdvice;

import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.service.UserService;
import org.jboss.logging.annotations.Pos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@RequestMapping("/api/account")
public class UserControllerAdvice extends ResponseEntityExceptionHandler {
    UserService userService;
    public UserControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(
                userService.createUser(createUserDto),
                HttpStatus.CREATED
        );
    }
}

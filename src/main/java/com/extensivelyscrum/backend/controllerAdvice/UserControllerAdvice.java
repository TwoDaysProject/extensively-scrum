package com.extensivelyscrum.backend.controllerAdvice;

import com.extensivelyscrum.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class UserControllerAdvice extends ResponseEntityExceptionHandler {
    UserService userService;
    public UserControllerAdvice(UserService userService) {
        this.userService = userService;
    }
}

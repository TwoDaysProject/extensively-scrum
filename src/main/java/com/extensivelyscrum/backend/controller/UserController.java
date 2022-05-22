package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.dto.SendMailDto;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.service.EmailService;
import com.extensivelyscrum.backend.service.UserService;
import org.jboss.logging.annotations.Pos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/account")
public class UserController {
    UserService userService;
    EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return new ResponseEntity<>(
                userService.createUser(createUserDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> delete(@PathVariable String userId) {
        userService.deleteUserWithId(userId);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PostMapping("/sendInvitationEmail")
    public ResponseEntity<Void> sendInvitationEmail(@RequestBody SendMailDto mailDto) {
        emailService.sendInvitationEmail(mailDto.toEmail());
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}

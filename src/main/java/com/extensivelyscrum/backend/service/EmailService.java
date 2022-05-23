package com.extensivelyscrum.backend.service;


import com.extensivelyscrum.backend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    private UserService userService;

    public EmailService(JavaMailSender mailSender, UserService userService){
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void sendSimpleEmail(
            String toEmail,
            String body,
            String subject
    ) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("kaixokoko1@gmail.com");
        msg.setTo(toEmail);
        msg.setSubject(subject);
        msg.setText(body);

        mailSender.send(msg);
    }

    public HttpStatus sendInvitationEmail(String email) {
        // this function will send an email with the link to create new password
        sendSimpleEmail(
                email,
                "Hi " + email + " you have been inveted to participate in a Extensively-scrum project\n" +
                "Please go to the link: www.google.com\n",
                "Invitation to Extensively-Scrum project"
        );
        return  HttpStatus.OK;
    }
}

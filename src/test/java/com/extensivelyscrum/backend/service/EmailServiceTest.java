package com.extensivelyscrum.backend.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    @Autowired
    private EmailService emailService;
    @RegisterExtension
    public final GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "test"))
            .withPerMethodLifecycle(false);

    @Test
    public void testSend() throws MessagingException {
        emailService.sendSimpleEmail(
                "test",
                "test",
                "test"
        );
        assertEquals("test", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
    }
}
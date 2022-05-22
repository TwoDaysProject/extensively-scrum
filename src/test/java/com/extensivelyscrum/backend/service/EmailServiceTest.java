package com.extensivelyscrum.backend.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailServiceTest {

    private final String CONTEXT_PATH = "api/account";

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8001;
    }

    @RegisterExtension
    public final GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "test"))
            .withPerMethodLifecycle(false);

    @Test
    public void testSend() throws MessagingException {

        // *** given
        Map<String,Object> request = new HashMap<>();
        request.put("toEmail", "test@spring.io");
        Response response =
            given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                    contentType("application/json").
                    accept("application/json").
                    body(request).

        // *** when
            when().
                    post(CONTEXT_PATH + "/sendInvitationEmail").

        // *** then
            then().
                    log().all().
                    statusCode(200).
                    extract().response();
        //assertEquals("test", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
    }
}
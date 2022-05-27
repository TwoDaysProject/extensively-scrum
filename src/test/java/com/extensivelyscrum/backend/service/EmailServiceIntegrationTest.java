package com.extensivelyscrum.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailServiceIntegrationTest {

    private final String CONTEXT_PATH = "api/account";
    private ObjectMapper mapper;
    private String email = "maymosun@gmail.com";
    private String password = "ghord";
    private String userID;
    private String jwtToken;
    private String fullName = "maymoun";

    @BeforeAll
    public void setUp() throws Exception {

        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8001;
        mapper = new ObjectMapper();

        //create user
        Map<String,Object> request = new HashMap<>();
        request.put("fullName",fullName);
        request.put("email",email);
        request.put("password",password);
        userID =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request).post("api/account/signup").then().extract().response().jsonPath().getString("id");

        //login user
        Map<String,Object> request2 = new HashMap<>();
        request2.put("email",email);
        request2.put("password",password);
        jwtToken =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request2).post("/login").then().extract().response().jsonPath().getString("token");
    }

    @Test
    public void testSend() throws MessagingException {

        // *** given
        Map<String,Object> request = new HashMap<>();
        request.put("email", "test@spring.io");
        Response response =
            given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                    contentType("application/json").
                    accept("application/json").
                    header("Authorization",jwtToken).
                    body(request).

        // *** when
            when().
                    post(CONTEXT_PATH + "/sendInvitationEmail").

        // *** then
            then().
                    log().all().
                    statusCode(200).
                    extract().response();
    }
}
package com.extensivelyscrum.backend.controller;
import static io.restassured.RestAssured.*;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static org.assertj.core.api.Assertions.assertThat;
import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.dto.JwtLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIntegrationTest {

    private final String CONTEXT_PATH = "api/account";
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8001;
        mapper = new ObjectMapper();
    }

    @Test
    public void testCreateUser() throws Exception {

        // *** given
        CreateUserDto createUserDto = new CreateUserDto("sosssmaaa","sofllm@gmail.com","soma");
        Map<String,Object> request = new HashMap<>();
        request.put("fullName",createUserDto.fullName());
        request.put("email",createUserDto.email());
        request.put("password",createUserDto.password());
        Response response =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request).

        // *** when
                when().
                        post(CONTEXT_PATH + "/signup").

        // *** then
                then().
                        log().all().
                        statusCode(201).
                        contentType("application/json").
                        extract().response();

        assertThat(response).isNotNull();
        assertEquals(createUserDto.fullName(),response.jsonPath().getString("fullName"));
        assertEquals(createUserDto.email(),response.jsonPath().getString("email"));

        // *** clear
        delete(CONTEXT_PATH + "/delete/" + response.jsonPath().getString("id"));

    }

    @Test
    public void testLoginUser() throws Exception {
        // *** given
        CreateUserDto createUserDto = new CreateUserDto("somaaa","so@gmail.com","soma");
        Map<String,Object> request = new HashMap<>();
        request.put("fullName",createUserDto.fullName());
        request.put("email",createUserDto.email());
        request.put("password",createUserDto.password());
        Response response = given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                contentType("application/json").
                accept("application/json").
                body(request).post(CONTEXT_PATH + "/signup").andReturn();
        JwtLoginDto jwtLoginDto = new JwtLoginDto(createUserDto.email(),createUserDto.password());
        Map<String,Object> request2 = new HashMap<>();
        request2.put("email",jwtLoginDto.email());
        request2.put("password",jwtLoginDto.password());

        Response response2 =
        given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                contentType("application/json").
                accept("application/json").
                body(request2).

        // *** when
        when().post("/login").

        // *** then
        then().log().all().
                statusCode(200).
                contentType("application/json").
                extract().response();

        assertThat(response2).isNotNull();
        Assert.hasText(response2.getBody().prettyPrint(), "Bearer");

        // *** clear
        delete(CONTEXT_PATH + "/delete/" + response.jsonPath().getString("id"));
    }
}
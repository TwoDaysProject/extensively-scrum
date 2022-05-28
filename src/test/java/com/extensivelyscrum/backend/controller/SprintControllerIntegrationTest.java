package com.extensivelyscrum.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class SprintControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(BacklogController.class);
    private final String CONTEXT_PATH = "api/sprint";
    private ObjectMapper mapper;
    private String email = "test5@gmail.com";
    private String password = "test";
    private String userID;
    private String jwtToken;
    private String fullName = "test";
    private String nameProject = "project";
    private String description = "descrip";
    private String projectID;

    @BeforeAll
    public void setUp() throws Exception {

        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8001;
        mapper = new ObjectMapper();

        //create user
        Map<String, Object> request = new HashMap<>();
        request.put("fullName", fullName);
        request.put("email", email);
        request.put("password", password);

        userID =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request).post("api/account/signup").then().extract().response().jsonPath().getString("id");

        //login user
        Map<String, Object> request2 = new HashMap<>();
        request2.put("email", email);
        request2.put("password", password);
        jwtToken =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request2).post("/login").then().extract().response().jsonPath().getString("token");
        //create project
        Map<String, Object> request3 = new HashMap<>();
        request3.put("name", nameProject);
        request3.put("description", description);
        try {
            projectID =
                    given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                            contentType("application/json").
                            header("Authorization", jwtToken).
                            accept("application/json").
                            body(request3).post("api/project/create").then().extract().response().jsonPath().getString("id");
        } catch (Exception e) {
            delete("api/account/delete/" + email);
        }
    }

    @AfterAll
    public void clear() {
        given().contentType("application/json").
                header("Authorization",jwtToken).
                accept("application/json").
                delete( CONTEXT_PATH +"/deleteProject/" + projectID);
        delete("api/account/delete/" + email);
    }

    @Test
    public void testCreateSprint() throws Exception {

        // ** given
        Map<String, Object> request = new HashMap<>();
        request.put("projectId", projectID);
        request.put("name", "new sprint");
        request.put("description", "a new sprint hhh");

        Response response = given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                header("Authorization", jwtToken).
                contentType("application/json").
                accept("application/json").
                body(request).

                // *** when
                        when().
                post(CONTEXT_PATH + "/create").

                // *** then
                        then().
                log().all().
                statusCode(201).
                extract().response();

        // clear backlog item:
        delete(CONTEXT_PATH + "/delete/" + response.jsonPath().getString("id"));

        assertEquals("new sprint", response.jsonPath().getString("name"));
        assertEquals("a new sprint hhh", response.jsonPath().getString("description"));
    }
}
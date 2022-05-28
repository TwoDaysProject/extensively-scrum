package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.CreateTicketDto;
import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketControllerIntegrationTest {

    private final String CONTEXT_PATH = "api/ticket";

    private ObjectMapper mapper;
    private String email = "maymodksdun@gmail.com";
    private String password = "somaya";
    private String userID;
    private String jwtToken;
    private String fullName = "somaya";
    private String nameProject = "project";
    private String description = "descrip";
    private String projectID;

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

        System.out.println("thiis" + userID);
        //login user
        Map<String,Object> request2 = new HashMap<>();
        request2.put("email",email);
        request2.put("password",password);
        jwtToken =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request2).post("/login").then().extract().response().jsonPath().getString("token");
        System.out.println("here" + jwtToken);

        //create project
        Map<String,Object> request3 = new HashMap<>();
        request3.put("name",nameProject);
        request3.put("description",description);
        try {
            projectID =
                    given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                            contentType("application/json").
                            header("Authorization",jwtToken).
                            accept("application/json").
                            body(request3).post("api/project/create").then().extract().response().jsonPath().getString("id");
        } catch (Exception e) {
            delete("api/account/delete/" + userID);
        }
    }

    @AfterAll
    public void clear() {
        delete("api/account/delete/" + userID);
        delete("api/project/deleteProject" + projectID);
    }


    @Test
    public void testNewProject () throws  Exception {

        //given:
        CreateTicketDto ticket = new CreateTicketDto("last", "last", projectID, null);
        Map<String, Object> request = new HashMap<>();
        request.put("name", ticket.name());
        request.put("description", ticket.description());
        request.put("projectId", ticket.projectId());

        String ticketId = null;

        try {
            Response response =
                    given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                            contentType("application/json").
                            header("Authorization", jwtToken).
                            accept("application/json").
                            body(request).
                            //When:
                                    when().
                            post(CONTEXT_PATH + "/create").
                            //Then:
                                    then().
                            log().all().
                            statusCode(201).
                            contentType("application/json").
                            extract().response();

            ticketId = response.jsonPath().getString("id");

            assertThat(response).isNotNull();
            assertEquals(ticket.name(), response.jsonPath().getString("name"));
            assertEquals(ticket.description(), response.jsonPath().getString("description"));
            assertNotNull(response.jsonPath().getString("tag"));

        } finally {
            delete(CONTEXT_PATH + "/delete/" + ticketId);
        }
    }

}
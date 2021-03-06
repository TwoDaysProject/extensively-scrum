package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectControllerIntegrationTest {

    private final String CONTEXT_PATH = "api/project";

    private ObjectMapper mapper;
    private String email = "testi@gmail.com";
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

    }

    @AfterAll
    public void clear() {
        delete("api/account/delete/" + email);
    }


    @Test
    public void testNewProject () throws  Exception{

        String projectID1 = null;

        //given:
        NewProjectDto newProject = new NewProjectDto("last","last");
        Map<String,Object> request = new HashMap<>();
        request.put("name",newProject.name());
        request.put("description",newProject.description());

        Response response =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        header("Authorization",jwtToken).
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

        projectID1 = response.jsonPath().getString("id");

        given().contentType("application/json").
                header("Authorization",jwtToken).
                accept("application/json").
                delete( CONTEXT_PATH +"/deleteProject/" + projectID1);

        assertThat(response).isNotNull();
        assertEquals(newProject.name(),response.jsonPath().getString("name"));
        assertEquals(newProject.description(),response.jsonPath().getString("description"));
    }

    @Test
    public void testGetUserProjects () throws  Exception{

        String projectID1 = null;
        //given:
        NewProjectDto newProject = new NewProjectDto("last","last");
        Map<String,Object> request = new HashMap<>();
        request.put("name",newProject.name());
        request.put("description",newProject.description());

        projectID1 =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        header("Authorization",jwtToken).
                        accept("application/json").
                        body(request).
                        when().
                        post(CONTEXT_PATH + "/create").
                        then().
                        extract().response().jsonPath().getString("id");

        Response response =
                given().contentType("application/json").
                        header("Authorization",jwtToken).
                        accept("application/json").
        //When:
                when().
                        get(CONTEXT_PATH + "/getUserProjects").
        //Then:
                then().
                        log().all().
                        statusCode(200).
                        contentType("application/json").
                        extract().response();

        given().contentType("application/json").
                header("Authorization",jwtToken).
                accept("application/json").
                delete( CONTEXT_PATH +"/deleteProject/" + projectID1);

        assertThat(response).isNotNull();
        List<HashMap> listProject = response.jsonPath().getList("");
        assertEquals(1, listProject.size());
        assertEquals(newProject.name(),listProject.get(0).get("name"));
        assertEquals(newProject.description(),listProject.get(0).get("description"));

    }

}
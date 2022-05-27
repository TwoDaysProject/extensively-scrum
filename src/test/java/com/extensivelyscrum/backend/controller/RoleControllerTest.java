package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.AddProjectMemberDto;
import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.dto.JwtLoginDto;
import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.enums.RoleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleControllerTest {


    private final String CONTEXT_PATH = "api/role";
    private ObjectMapper mapper;
    private String email = "maymoun@gmail.com";
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
        projectID =
        given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request3).post("api/project/newProject").then().extract().response().jsonPath().getString("id");
    }

    @Test
    public void testAddProjectMember() throws Exception {

        // *** given
        Map<String,Object> request4 = new HashMap<>();
        request4.put("idProject",projectID);
        request4.put("UserEmail",email);
        request4.put("role",RoleEnum.SCRUM_MASTER);
        given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        header("Authorization",jwtToken).
                        contentType("application/json").
                        accept("application/json").
                        body(request4).

        // *** when
        when().
                        post(CONTEXT_PATH + "/addProjectMember").
        // *** then
        then().
                        log().all().
                        statusCode(200);

        // *** clear
        delete(CONTEXT_PATH + "/delete/" + userID);
    }
}

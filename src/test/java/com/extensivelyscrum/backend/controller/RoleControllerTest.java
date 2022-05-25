package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.AddProjectMemberDto;
import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.dto.JwtLoginDto;
import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.enums.RoleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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
    private Response response, response2, response3;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8001;
        mapper = new ObjectMapper();

        //create user
        CreateUserDto createUserDto = new CreateUserDto("somaaa","so@gmail.com","soma");
        Map<String,Object> request = new HashMap<>();
        request.put("fullName",createUserDto.getFullName());
        request.put("email",createUserDto.getEmail());
        request.put("password",createUserDto.getPassword());
        response = given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                contentType("application/json").
                accept("application/json").
                body(request).post("api/account/signup").andReturn();

        //login user
        JwtLoginDto jwtLoginDto = new JwtLoginDto(createUserDto.getEmail(),createUserDto.getPassword());
        Map<String,Object> request2 = new HashMap<>();
        request2.put("email",jwtLoginDto.getEmail());
        request2.put("password",jwtLoginDto.getPassword());
        response2 =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request2).post("/login").andReturn();
        //create project
        NewProjectDto newProject = new NewProjectDto("last","last");
        Map<String,Object> request3 = new HashMap<>();
        request3.put("name",newProject.getName());
        request3.put("description",newProject.getDescription());
        response3 = given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        header("Authorization",response2.body().jsonPath().getString("token")).
                        contentType("application/json").
                        accept("application/json").
                        body(request3).post("api/project/newProject").then().extract().response();
    }

    @Test
    public void testAddProjectMember() throws Exception {

        // *** given
        System.out.println("");
        AddProjectMemberDto addProjectMemberDto = new AddProjectMemberDto(response3.jsonPath().getString("id"),"soma@gmail.com", RoleEnum.SCRUM_MASTER);
        Map<String,Object> request4 = new HashMap<>();
        request4.put("idProject",addProjectMemberDto.idProject());
        request4.put("UserEmail",addProjectMemberDto.UserEmail());
        request4.put("role",addProjectMemberDto.role());
        given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        header("Authorization",response2.body().jsonPath().getString("token")).
                        contentType("application/json").
                        accept("application/json").
                        body(request4).

        // *** when
        when().
                        post(CONTEXT_PATH + "/addProjectMember").

        // *** then
        then().
                        log().all().
                        statusCode(200).
                        contentType("application/json");

        // *** clear
        delete(CONTEXT_PATH + "/delete/" + response.jsonPath().getString("id"));
    }
}

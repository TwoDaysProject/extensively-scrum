/*package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.model.Project;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectControllerTest {

    private final String CONTEXT_PATH = "api/project";

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8001;
    }

    @Test
    public void testNewProject () throws  Exception{

        //given:
        NewProjectDto newProject = new NewProjectDto("last","last");
        Map<String,Object> request = new HashMap<>();
        request.put("name",newProject.getName());
        request.put("description",newProject.getDescription());

        Response response =
                given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                        contentType("application/json").
                        accept("application/json").
                        body(request).
        //When:
                when().
                        post(CONTEXT_PATH + "/newProject").
        //Then:
                then().
                        log().all().
                        statusCode(201).
                        contentType("application/json").
                        extract().response();
        assertThat(response).isNotNull();
        assertEquals(newProject.getName(),response.jsonPath().getString("name"));
        assertEquals(newProject.getDescription(),response.jsonPath().getString("description"));
    }

}*/
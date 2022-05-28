package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.ListBacklogItemsDto;
import com.extensivelyscrum.backend.enums.BackLogType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class BacklogControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(BacklogController.class);
    private final String CONTEXT_PATH = "api/backlog";
    private ObjectMapper mapper;
    private String email = "test125@gmail.com";
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
        delete("api/project/deleteProject/" + projectID);
        delete("api/account/delete/" + userID);
    }

    @Test
    public void testCreateBackLogItem() {

        String backlogId = null;

        try {
            // ** given
            Map<String,Object> request = new HashMap<>();
            request.put("projectId",projectID);
            request.put("type", BackLogType.STORY);
            request.put("name", "new story");
            request.put("description", "a new story hhh");

            Response response = given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                    header("Authorization",jwtToken).
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

            backlogId = response.jsonPath().getString("id");

            assertEquals("new story", response.jsonPath().getString("name"));
            assertEquals("a new story hhh", response.jsonPath().getString("description"));
            assertEquals(BackLogType.STORY, BackLogType.valueOf(response.jsonPath().getString("type")));
        } finally {
            // clear backlog item:
            delete(CONTEXT_PATH + "/delete/" + backlogId);
        }
    }

    @Test
    public void testListBacklogItems() {
        String backlogId = null;
        try {
            // ** given
            Map<String,Object> request = new HashMap<>();
            request.put("projectId",projectID);
            request.put("type", BackLogType.STORY);
            request.put("name", "new story");
            request.put("description", "a new story hhh");

            backlogId = given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                    header("Authorization",jwtToken).
                    contentType("application/json").
                    accept("application/json").
                    body(request).
                    when().
                    post(CONTEXT_PATH + "/create").
                    then().
                    extract().response().jsonPath().getString("id");

            Response response = given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                    header("Authorization",jwtToken).
                    contentType("application/json").
                    accept("application/json").
                    when().
                    get(CONTEXT_PATH + "/list/" + projectID).
                    then().
                    statusCode(200).
                    extract().response();

            List<HashMap> listBacklogItemsDtos = response.jsonPath().getList("");
            assertEquals(1, listBacklogItemsDtos.size());
            assertEquals("new story", listBacklogItemsDtos.get(0).get("name"));
            assertEquals("a new story hhh", listBacklogItemsDtos.get(0).get("description"));
            assertEquals(BackLogType.STORY, BackLogType.valueOf((String) listBacklogItemsDtos.get(0).get("type")));
        } finally {
            // clear backlog item:
            delete(CONTEXT_PATH + "/delete/" + backlogId);
        }
    }
}
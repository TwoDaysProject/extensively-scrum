/*package com.extensivelyscrum.backend.controllerAdvice;

import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.extensivelyscrum.backend.dto.CreateUserDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerAdviceTest {

    @Test
    public void testCreateUser() throws Exception {

        // *** given
        CreateUserDto createUserDto = new CreateUserDto("soma","soma@gmail.com","soma");
        Map<String,Object> request = new HashMap<>();
        request.put("fullName",createUserDto.getFullName());
        request.put("email",createUserDto.getEmail());
        request.put("password",createUserDto.getPassword());
        Response response =
        given().config(RestAssured.config().decoderConfig(decoderConfig().contentDecoders(DEFLATE))).
                contentType("application/json").
                accept("application/json").
                body(request).

        // *** when
        when().
                post("/api/account/signup").

        // *** then
        then().
                log().all().
                statusCode(201).
                contentType("application/json").
                extract().response();
        assertThat(response).isNotNull();
        assertEquals(createUserDto.getFullName(),response.jsonPath().getString("fullName"));
        assertEquals(createUserDto.getEmail(),response.jsonPath().getString("email"));
        assertEquals(createUserDto.getPassword(),response.jsonPath().getString("password"));
    }
}
*/
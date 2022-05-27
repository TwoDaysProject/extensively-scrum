package com.extensivelyscrum.backend.dto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.extensivelyscrum.backend.security.JwtProperties;


public record JwtLoginDto(
        String email,
        String password
) {

    public JwtLoginDto (String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static String getEmailFromJwtToken(String jwtToken) {
        jwtToken = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(jwtToken)
                .getSubject();
        return email;
    }
}

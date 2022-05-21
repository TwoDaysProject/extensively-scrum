package com.extensivelyscrum.backend.security;

public class JwtProperties {
    public static final String SECRET = "my_very@VERY_complicated+Jwt-secret-for-extensively-scRUM/APPLICATION";
    public static final int EXPIRATION_TIME = 300000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}

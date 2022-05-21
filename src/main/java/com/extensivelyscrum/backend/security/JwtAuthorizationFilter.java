package com.extensivelyscrum.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserService userService ) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtToken = request.getHeader(JwtProperties.HEADER);
        if (jwtToken == null || !jwtToken.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication auth = getEmailPasswordAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (Throwable ignored) {}
    }

    private Authentication getEmailPasswordAuthentication(String jwtToken) throws Throwable {
        if (jwtToken != null) {
            jwtToken = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
            String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(jwtToken)
                    .getSubject();
            UserPrincipal principal = new UserPrincipal(userService.getUserWithEmail(email), userService);
            return new UsernamePasswordAuthenticationToken(principal.getUsername(), null, principal.getAuthorities());
        }
        return null;
    }
}

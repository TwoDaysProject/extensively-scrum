package com.extensivelyscrum.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.service.ProjectService;
import com.extensivelyscrum.backend.service.RoleService;
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
    ProjectService projectService;

    private RoleService roleService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  ProjectService projectService,
                                  RoleService roleService,
                                  UserService userService ) {
        super(authenticationManager);
        this.userService = userService;
        this.projectService = projectService;
        this.roleService = roleService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtToken = request.getHeader(JwtProperties.HEADER);
        String projectId = request.getHeader("projectId");
        if (jwtToken == null || !jwtToken.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication auth = getEmailPasswordAuthentication(jwtToken, projectId);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (Throwable ignored) {}
    }

    private Authentication getEmailPasswordAuthentication(String jwtToken, String projectId) throws Throwable {
        if (jwtToken != null) {
            Project project = projectService.getProjectWithId(projectId);
            jwtToken = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
            String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(jwtToken)
                    .getSubject();
            UserPrincipal principal = new UserPrincipal(userService.getUserWithEmail(email), project, userService, roleService);
            return new UsernamePasswordAuthenticationToken(principal.getUsername(), null, principal.getAuthorities());
        }
        return null;
    }
}

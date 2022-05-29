package com.extensivelyscrum.backend.security;

import com.extensivelyscrum.backend.service.ProjectService;
import com.extensivelyscrum.backend.service.RoleService;
import com.extensivelyscrum.backend.service.UserPrincipalDetailService;
import com.extensivelyscrum.backend.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailService userPrincipalDetailService;
    private UserService userService;

    private ProjectService projectService;

    private RoleService roleService;
    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService,
                                 ProjectService projectService,
                                 RoleService roleService,
                                 UserService userService){
        this.roleService = roleService;
        this.userService = userService;
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.projectService = projectService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().defaultsDisabled();
        http.cors();
        http
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // filters:
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.projectService, this.roleService, this.userService))
                // Authorizations:
                .authorizeRequests()
                .antMatchers("/api/account/signup").permitAll()
                .antMatchers("/api/account/delete/*").permitAll()
                .antMatchers("/api/role/delete/*").permitAll()
                .antMatchers("/api/project/deleteProject/*").permitAll()
                .antMatchers("/api/project/create").authenticated()
                /*.antMatchers("/api/project/getUserProjects").hasAnyRole(
                        "ROLE_SCRUM_MASTER", "ROLE_DEV_TEAM_MEMBER", "ROLE_PRODUCT_OWNER"
                )*/
                .anyRequest().authenticated()
        ;
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
        return authenticationProvider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(List.of("X-PINGOTHER", "Authorization", "Cache-Control", "Content-Type", "projectId", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        configuration.addExposedHeader("Authorization");
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
package com.sungwon.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //스웨어, API role
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/swagger-ui/**", "/swagger-ui/","/v3/api-docs/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/v2/api-docs").permitAll()
                .requestMatchers("/api/common/**").permitAll()
                .requestMatchers("/api/member/**").permitAll()
                .requestMatchers("/api/menu/**").permitAll()
                .requestMatchers("/api/role/**").permitAll()
                .requestMatchers("/api/team/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/elastic/**").permitAll()
        );

        return http.build();
    }
}
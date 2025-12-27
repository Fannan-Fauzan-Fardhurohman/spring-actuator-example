package com.example.paymentgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity in this demo
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to Prometheus endpoint for scraping
                        .requestMatchers("/actuator/prometheus").permitAll()
                        // Allow public access to health and info
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        // Require authentication for all other actuator endpoints
                        .requestMatchers("/actuator/**").authenticated()
                        // Allow all business API endpoints
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(basic -> {
                }); // Enable Basic Auth for secured endpoints

        return http.build();
    }
}

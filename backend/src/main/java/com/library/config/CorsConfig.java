package com.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * CORS Configuration.
 *
 * WHY do we need this?
 * React runs on localhost:5173 and Spring Boot on localhost:8080.
 * Browsers block requests between different origins (ports) by default.
 * This config tells Spring Boot: "Allow requests from the React frontend."
 */
@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins:http://localhost:5173}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow the React frontend origin
        config.setAllowedOrigins(List.of(allowedOrigins.split(",")));

        // Allow these HTTP methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers
        config.setAllowedHeaders(List.of("*"));

        // Allow credentials (cookies, auth headers)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Apply CORS config to all API endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

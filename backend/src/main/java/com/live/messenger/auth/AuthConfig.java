package com.live.messenger.auth;

import com.live.messenger.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up security configurations, including JWT authentication.
 */
@Configuration
public class AuthConfig {

    @Autowired
    private JwtFilter jwtFilter;  // JWT filter for validating tokens in incoming requests

    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Add the JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // Configure authorization for specific HTTP requests
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            // Allow all POST requests to /users and /auth/login without authentication
                            .requestMatchers(HttpMethod.POST, "/users").permitAll()
                            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                            // Allow WebSocket connections
                            .requestMatchers("/ws").permitAll();
                    // All other requests require authentication
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })

                // Disable HTTP Basic authentication
                .httpBasic(AbstractHttpConfigurer::disable)

                // Disable Cross-Site Request Forgery (CSRF) protection
                .csrf(AbstractHttpConfigurer::disable)

                // Build and return the configured SecurityFilterChain
                .build();
    }
}

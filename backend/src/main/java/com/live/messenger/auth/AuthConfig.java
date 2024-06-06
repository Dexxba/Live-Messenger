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

@Configuration
public class AuthConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(HttpMethod.POST, "/users").permitAll()
                            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    // All other requests require authentication
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })
                // Disable HTTP Basic authentication
                .httpBasic(AbstractHttpConfigurer::disable)
                // Disable Cross-Site Request Forgery (CSRF) protection
                .csrf(AbstractHttpConfigurer::disable)
                // Build the SecurityFilterChain
                .build();
    }
}

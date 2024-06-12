package com.live.messenger.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) settings.
 */
@Configuration
public class CorsConfig {

    /**
     * Configures global CORS settings.
     *
     * @return a WebMvcConfigurer instance for customizing CORS settings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow CORS requests from any origin to any endpoint
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}

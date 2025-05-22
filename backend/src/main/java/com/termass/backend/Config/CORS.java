package com.termass.backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) policies.
 * <p>
 * This class implements {@link WebMvcConfigurer} and overrides the
 * {@code addCorsMappings} method to allow cross-origin requests from any origin.
 * It enables the following HTTP methods: GET, POST, PUT, DELETE.
 * All headers are allowed in the requests.
 * </p>
 *
 * <p><strong>Usage:</strong> This configuration is automatically picked up by
 * Spring Boot due to the {@link Configuration} annotation.</p>
 *
 * @author Rachit
 */
@Configuration
public class CORS implements WebMvcConfigurer {

    /**
     * Configure CORS mappings for the application.
     * <p>
     * Allows requests from any origin ({@code *}) and permits the use of
     * HTTP methods GET, POST, PUT, and DELETE across all endpoints ({@code /**}).
     * Also allows all headers to be included in the requests.
     * </p>
     *
     * @param registry the {@link CorsRegistry} to which CORS mappings are added
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}

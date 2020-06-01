package com.smolderingdrake.homelibrarycore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowCredentials(false)
                .allowedHeaders("Access-Control-Allow-Origin")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .maxAge(3600);
    }
}

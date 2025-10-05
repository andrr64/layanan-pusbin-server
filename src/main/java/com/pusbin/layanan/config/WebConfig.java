package com.pusbin.layanan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // semua endpoint
                        .allowedOrigins("http://localhost:3000") // frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true) // wajib kalau pakai cookie
                        .allowedHeaders("*")
                        .allowedOriginPatterns("*")
                        .exposedHeaders("Set-Cookie"); // agar browser bisa menerima cooki
            }
        };
    }
}

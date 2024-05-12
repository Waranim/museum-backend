package org.example.museumbackend.adapter.keycloak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${app.keycloak.auth-url:http://localhost:8081/realms/museum/protocol/openid-connect}")
    private String authUrl;

    @Value("${app.keycloak.client-id:museum-backend}")
    private String clientId;

    @Value("${app.keycloak.client-secret:vBFffuhSkZkREDDPcgKQ86KjMqSsuJkw}")
    private String clientSecret;

    @Value("${app.keycloak.admin-url:http://localhost:8081/admin/realms/museum}")
    private String adminUrl;

    @Bean
    public String authUrl() {
        return authUrl;
    }

    @Bean
    public String clientId() {
        return clientId;
    }

    @Bean
    public String clientSecret() {
        return clientSecret;
    }

    @Bean
    public String adminUrl() {
        return adminUrl;
    }
}

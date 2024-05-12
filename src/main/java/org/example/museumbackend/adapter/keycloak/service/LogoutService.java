package org.example.museumbackend.adapter.keycloak.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.museumbackend.adapter.keycloak.common.AuthResponse;
import org.example.museumbackend.adapter.web.DTO.request.RefreshTokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
@Log4j2
@RequiredArgsConstructor
public class LogoutService {

    private static final String LOGOUT_PATH = "/logout";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final String authUrl;
    private final String clientId;
    private final String clientSecret;

//    @Value("${app.keycloak.auth-url:http://localhost:8081/realms/museum/protocol/openid-connect}")
//    private String authUrl;
//
//    @Value("${app.keycloak.client-id:museum-backend}")
//    private String clientId;
//
//    @Value("${app.keycloak.client-secret:vBFffuhSkZkREDDPcgKQ86KjMqSsuJkw}")
//    private String clientSecret;

    private final WebClient webClient = WebClient.create();

    public ResponseEntity<?> logout(RefreshTokenDTO refreshTokenDTO) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            paramMap.add(CLIENT_ID, clientId);
            paramMap.add(CLIENT_SECRET, clientSecret);
            paramMap.add(REFRESH_TOKEN, refreshTokenDTO.refreshToken());

            var PATH = authUrl + LOGOUT_PATH;

            log.info("Try to logout user: " + jwtAuthenticationToken.getName());

            var response = webClient
                    .post()
                    .uri(URI.create(PATH))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(paramMap))
                    .retrieve()
                    .bodyToMono(AuthResponse.class)
                    .block();

            log.info("Authentication success user: " + jwtAuthenticationToken.getName());

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

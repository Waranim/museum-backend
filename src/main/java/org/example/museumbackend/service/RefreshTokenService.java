package org.example.museumbackend.service;

import lombok.extern.log4j.Log4j2;
import org.example.museumbackend.adapter.keycloak.common.AuthResponse;
import org.example.museumbackend.adapter.web.DTO.request.RefreshTokenDTO;
import org.example.museumbackend.adapter.web.DTO.response.TokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
@Log4j2
public class RefreshTokenService {

    private static final String TOKEN_PATH = "/token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REFRESH_TOKEN = "refresh_token";

    @Value("${app.keycloak.auth-url:http://localhost:8081/realms/museum/protocol/openid-connect}")
    private String authUrl;

    @Value("${app.keycloak.client-id:museum-backend}")
    private String clientId;

    @Value("${app.keycloak.client-secret:vBFffuhSkZkREDDPcgKQ86KjMqSsuJkw}")
    private String clientSecret;

    private final WebClient webClient = WebClient.create();

    public TokenDTO updateRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add(CLIENT_ID, clientId);
        paramMap.add(CLIENT_SECRET, clientSecret);
        paramMap.add(REFRESH_TOKEN, refreshTokenDTO.refreshToken());
        paramMap.add(GRANT_TYPE, REFRESH_TOKEN);

        var PATH = authUrl + TOKEN_PATH;

        log.info("Try to update refresh token");

        var response = webClient
                .post()
                .uri(URI.create(PATH))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(paramMap))
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();

        log.info("Update refresh token successful");

        return TokenDTO.authResponse(response);
    }
}

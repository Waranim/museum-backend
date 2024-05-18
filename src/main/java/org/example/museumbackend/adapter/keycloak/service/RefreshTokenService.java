package org.example.museumbackend.adapter.keycloak.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.museumbackend.adapter.keycloak.common.AuthResponse;
import org.example.museumbackend.adapter.web.DTO.request.RefreshTokenDTO;
import org.example.museumbackend.adapter.web.DTO.response.TokenDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
@Log4j2
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final String TOKEN_PATH = "/token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final String authUrl;
    private final String clientId;
    private final String clientSecret;

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

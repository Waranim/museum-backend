package org.example.museumbackend.adapter.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.museumbackend.adapter.keycloak.common.AuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthClient {

    private static final String TOKEN_PATH = "/token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    public static final String CLIENT_CREDENTIALS = "client_credentials";

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

    public AuthResponse authenticate() {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add(CLIENT_ID, clientId);
        paramMap.add(CLIENT_SECRET, clientSecret);
        paramMap.add(GRANT_TYPE, CLIENT_CREDENTIALS);

        var url = authUrl + TOKEN_PATH;

        log.info("Try to authenticate");

        var response = webClient
                .post()
                .uri(URI.create(url))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(paramMap))
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();

        log.info("Authentication success");

        return response;
    }
}

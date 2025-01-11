package org.example.museumbackend.adapter.keycloak.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.example.museumbackend.adapter.keycloak.DTO.PasswordDTO;
import org.example.museumbackend.adapter.keycloak.common.AccessTokenClient;
import org.example.museumbackend.adapter.web.DTO.request.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class RegisterService {

    private static final String BEARER = "Bearer ";
    private static final String USERS_PATH = "/users";
    private static final String VERIFY_EMAIL_PATH = "/send-verify-email";
    private static final String AUTHORIZATION = "Authorization";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CREDENTIALS = "credentials";
    private static final String ENABLED = "enabled";

    private final String adminUrl;

    AccessTokenClient accessTokenClient;
    WebClient webClient = WebClient.create();

    public ResponseEntity<?> register(UserDTO userDTO) {
        Map<String, Object> body = new HashMap<>();
        body.put(EMAIL, userDTO.email());
        body.put(ENABLED, true);

        PasswordDTO[] credentials = { new PasswordDTO(false, PASSWORD, userDTO.password()) };
        body.put(CREDENTIALS, credentials);

        var PATH = adminUrl + USERS_PATH;

        var authToken = BEARER + accessTokenClient.getAccessToken();

        var registerResponse = webClient
                .post()
                .uri(URI.create(PATH))
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, authToken)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .block();

        if (registerResponse != null && registerResponse.getStatusCode() == HttpStatus.CREATED) {
            var location = registerResponse.getHeaders().getLocation();

            if (location != null) {
                var locationPath = location.getPath();
//                var userId = locationPath.split("/")[locationPath.split("/").length - 1];
                var userId = locationPath.substring(locationPath.lastIndexOf('/') + 1);
                var verifyEmailPath = PATH + "/" + userId + VERIFY_EMAIL_PATH;
                var verifyEmailResponse = webClient
                        .put()
                        .uri(URI.create(verifyEmailPath))
                        .header(AUTHORIZATION, authToken)
                        .retrieve()
                        .toBodilessEntity()
                        .block();

                if (verifyEmailResponse != null && verifyEmailResponse.getStatusCode().is2xxSuccessful()) {
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Что-то пошло не так при регистрации пользователя");
    }
}

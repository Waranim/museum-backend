package org.example.museumbackend.adapter.keycloak.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.museumbackend.adapter.keycloak.AuthClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Getter
@Log4j2
@RequiredArgsConstructor
public class AccessTokenClient {

    private String accessToken;

    private final AuthClient authClient;

    @Scheduled(fixedDelayString = "${app.keycloak.express-in.access-token:300000}")
    private void updateAccessToken() {
        log.info("Updating access token");
        var response = authClient.authenticate();
        accessToken = response.getAccessToken();
        log.info("Updated access token");
    }
}

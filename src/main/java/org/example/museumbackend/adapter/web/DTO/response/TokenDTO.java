package org.example.museumbackend.adapter.web.DTO.response;

import org.example.museumbackend.adapter.keycloak.common.AuthResponse;

public record TokenDTO(
        String access_token,
        Integer expires_in,
        Integer refresh_expires_in,
        String refresh_token,
        String token_type,
        String not_before_policy,
        String session_state,
        String scope
) {
    public static TokenDTO authResponse(AuthResponse authResponse) {
        return new TokenDTO(
                authResponse.getAccessToken(),
                authResponse.getExpiresIn(),
                authResponse.getRefreshExpiresIn(),
                authResponse.getRefreshToken(),
                authResponse.getTokenType(),
                authResponse.getNotBeforePolicy(),
                authResponse.getSessionState(),
                authResponse.getScope()
        );
    }
}

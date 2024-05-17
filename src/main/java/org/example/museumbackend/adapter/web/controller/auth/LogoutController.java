package org.example.museumbackend.adapter.web.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.keycloak.service.LogoutService;
import org.example.museumbackend.adapter.web.DTO.request.RefreshTokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/logout", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class LogoutController {

    LogoutService logoutService;

    @PostMapping
    public ResponseEntity<?> logout(@Validated @RequestBody RefreshTokenDTO refreshTokenDTO) {
        return logoutService.logout(refreshTokenDTO);
    }
}

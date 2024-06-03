package org.example.museumbackend.adapter.web.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Контроль доступа")
@RestController
@RequestMapping(value = "/api/logout", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class LogoutController {

    LogoutService logoutService;

    @Operation(
            summary = "Выход пользователя из системы, доступно только для авторизированных пользователей"
    )
    @PostMapping
    public ResponseEntity<?> logout(@Validated @RequestBody RefreshTokenDTO refreshTokenDTO) {
        return logoutService.logout(refreshTokenDTO);
    }
}

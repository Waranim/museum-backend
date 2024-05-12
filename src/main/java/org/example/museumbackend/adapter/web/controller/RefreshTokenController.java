package org.example.museumbackend.adapter.web.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.RefreshTokenDTO;
import org.example.museumbackend.adapter.web.DTO.response.TokenDTO;
import org.example.museumbackend.adapter.keycloak.service.RefreshTokenService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/token")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RefreshTokenController {

    RefreshTokenService refreshTokenService;

    @PostMapping("/update")
    public TokenDTO updateRefreshToken(@Validated @RequestBody RefreshTokenDTO refreshTokenDTO) {
        return refreshTokenService.updateRefreshToken(refreshTokenDTO);
    }
}

package org.example.museumbackend.adapter.web.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.UserDTO;
import org.example.museumbackend.adapter.web.DTO.response.TokenDTO;
import org.example.museumbackend.adapter.keycloak.service.LoginService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/login")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class LoginController {

    LoginService loginService;

    @PostMapping
    public TokenDTO login(@Validated @RequestBody UserDTO userDTO) {
        return loginService.login(userDTO);
    }
}

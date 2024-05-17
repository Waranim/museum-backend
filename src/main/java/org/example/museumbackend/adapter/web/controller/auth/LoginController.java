package org.example.museumbackend.adapter.web.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.keycloak.service.LoginService;
import org.example.museumbackend.adapter.web.DTO.request.UserDTO;
import org.example.museumbackend.adapter.web.DTO.response.TokenDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/public/api/login", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = PRIVATE)
public class LoginController {

    LoginService loginService;

    @PostMapping
    public TokenDTO login(@Validated @RequestBody UserDTO userDTO) {
        return loginService.login(userDTO);
    }
}

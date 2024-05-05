package org.example.museumbackend.adapter.web.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.UserDTO;
import org.example.museumbackend.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/api/register")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegisterController {

    RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> register(@Validated @RequestBody UserDTO userDTO) {
        return registerService.register(userDTO);
    }
}

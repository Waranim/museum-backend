package org.example.museumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.UserRepository;
import org.example.museumbackend.adapter.web.DTO.request.UserDTO;
import org.example.museumbackend.domain.UserEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserService {

    UserRepository userRepository;

    public void saveUser(UserDTO userDTO) {
        var userEntity = new UserEntity();
        userEntity.setUsername(userDTO.email());

        userRepository.save(userEntity);
    }

    public UserEntity getUser(SecurityContext securityContext) {
        if (securityContext.getAuthentication() instanceof JwtAuthenticationToken jwtAuthenticationToken) {

            return userRepository.findByUsername(jwtAuthenticationToken.getName());
        }

        throw new ResponseStatusException(UNAUTHORIZED);
    }
}

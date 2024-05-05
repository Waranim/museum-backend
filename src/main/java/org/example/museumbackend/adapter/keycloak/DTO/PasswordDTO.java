package org.example.museumbackend.adapter.keycloak.DTO;

public record PasswordDTO(Boolean temporary, String type, String value) {
}

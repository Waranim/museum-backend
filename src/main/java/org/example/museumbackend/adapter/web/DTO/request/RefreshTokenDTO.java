package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenDTO(@NotNull
                              @NotEmpty
                              String refreshToken) {
}

package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotNull;

public record TypeOfEventDTO(@NotNull String name) {
}

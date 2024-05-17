package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotNull;

public record PriceDTO(
        @NotNull
        Integer price,
        @NotNull
        Integer age
) {
}

package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewsReqDTO(
        @NotNull
        @NotEmpty
        String title,
        @NotNull
        @NotEmpty
        String summary,
        @NotNull
        @NotEmpty
        String content
) {
}

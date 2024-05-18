package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TypeOfEventReqDTO(@NotNull
                                @NotEmpty
                                String name) {
}

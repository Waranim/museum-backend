package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotNull;

public record TicketReqDTO(
        @NotNull
        Long event_id,
        @NotNull
        Long price_id,
        Boolean booked,
        Boolean paid
) {
}

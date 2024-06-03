package org.example.museumbackend.adapter.web.DTO.response;

import java.sql.Timestamp;

public record TicketResDTO(
        Long id,
        String event_name,
        Integer price,
        Boolean booked,
        Timestamp booking_time,
        Boolean paid,
        Timestamp payment_time
) {
}

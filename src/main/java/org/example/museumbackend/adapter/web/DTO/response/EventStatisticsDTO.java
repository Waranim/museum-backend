package org.example.museumbackend.adapter.web.DTO.response;

import java.sql.Timestamp;

public record EventStatisticsDTO(Long eventId, Long viewCount, Long totalBooked, Long totalPaid, Timestamp date) {
}

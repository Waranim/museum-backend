package org.example.museumbackend.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.EventRepository;
import org.example.museumbackend.adapter.repository.TicketRepository;
import org.example.museumbackend.adapter.specification.EventSpecifications;
import org.example.museumbackend.adapter.web.DTO.request.EventStatisticsReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.EventStatisticsDTO;
import org.example.museumbackend.domain.EventEntity;
import org.example.museumbackend.domain.TicketEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventStatisticsService {

    EventRepository eventRepository;
    TicketRepository ticketRepository;

    @SneakyThrows
    public List<EventStatisticsDTO> getEventStatistics(EventStatisticsReqDTO reqDTO) {
        var dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        var startDate = new Timestamp(dateFormat.parse(reqDTO.startDate()).getTime());
        var endDate = new Timestamp(dateFormat.parse(reqDTO.endDate()).getTime());

        return getEventStatistics(startDate, endDate, reqDTO.isCompleted());
    }

    public List<EventStatisticsDTO> getEventStatistics(Timestamp startDate, Timestamp endDate, Boolean completed) {
        Specification<EventEntity> eventSpec = Specification
                .where(EventSpecifications.hasDateBetween(startDate, endDate))
                .and(EventSpecifications.isCompleted(completed));

        List<EventEntity> events = eventRepository.findAll(eventSpec);
        List<EventStatisticsDTO> statistics = new ArrayList<>();

        for (EventEntity event : events) {
            Long totalBooked = ticketRepository.count(isBookedForEvent(event));
            Long totalPaid = ticketRepository.count(isPaidForEvent(event));

            EventStatisticsDTO dto = new EventStatisticsDTO(
                    event.getId(),
                    event.getViewCount(),
                    totalBooked,
                    totalPaid,
                    event.getDate()
            );
            statistics.add(dto);
        }

        return statistics;
    }

    private Specification<TicketEntity> isBookedForEvent(EventEntity event) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.isTrue(root.get("booked")),
                        criteriaBuilder.equal(root.get("event"), event)
                );
    }

    private Specification<TicketEntity> isPaidForEvent(EventEntity event) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.isTrue(root.get("paid")),
                        criteriaBuilder.equal(root.get("event"), event)
                );
    }
}

package org.example.museumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.EventRepository;
import org.example.museumbackend.adapter.repository.PriceRepository;
import org.example.museumbackend.adapter.repository.TicketRepository;
import org.example.museumbackend.adapter.web.DTO.request.TicketReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.TicketResDTO;
import org.example.museumbackend.domain.TicketEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TicketService {

    private static final String PRICE_NOT_AVAILABLE = "Price not available";
    private static final String INCORRECT_BOOKED_AND_PAID_FIELDS = "Incorrect booked and paid fields";
    private static final String INVALID_ID = "Invalid id";
    TicketRepository ticketRepository;
    EventRepository eventRepository;
    PriceRepository priceRepository;

    UserService userService;

    public TicketResDTO createTicket(TicketReqDTO ticketDTO) {
        var ticketEntity = new TicketEntity();
        var eventOptional = eventRepository.findById(ticketDTO.event_id());
        var priceOptional = priceRepository.findById(ticketDTO.price_id());
        var user = userService.getUser(SecurityContextHolder.getContext());

        if (eventOptional.isPresent() && priceOptional.isPresent()) {
            var event = eventOptional.get();
            var price = priceOptional.get();
            var prices = event.getPrices();

            if (!prices.contains(price)) {
                throw new ResponseStatusException(BAD_REQUEST, PRICE_NOT_AVAILABLE);
            }

            if (ticketDTO.paid() && Boolean.FALSE.equals(ticketDTO.booked())) {
                ticketEntity.setPaid(true);
                ticketEntity.setPrice(price.getPrice());
                ticketEntity.setEvent(event);
                ticketEntity.setUser(user);
                ticketEntity.setPaymentTime(new Timestamp(System.currentTimeMillis()));

                ticketRepository.save(ticketEntity);

                return new TicketResDTO(
                        ticketEntity.getId(),
                        event.getName(),
                        ticketEntity.getPrice(),
                        ticketEntity.getBooked(),
                        ticketEntity.getBookingTime(),
                        ticketEntity.getPaid(),
                        ticketEntity.getPaymentTime()
                );
            } else if (ticketDTO.booked() && Boolean.FALSE.equals(ticketDTO.paid())) {
                ticketEntity.setBooked(true);
                ticketEntity.setPrice(price.getPrice());
                ticketEntity.setEvent(event);
                ticketEntity.setUser(user);
                ticketEntity.setBookingTime(Timestamp.valueOf(Timestamp.from(java.time.Instant.now()).toLocalDateTime().plus(Duration.parse(event.getBookingTime()))));

                ticketRepository.save(ticketEntity);

                return new TicketResDTO(
                        ticketEntity.getId(),
                        event.getName(),
                        ticketEntity.getPrice(),
                        ticketEntity.getBooked(),
                        ticketEntity.getBookingTime(),
                        ticketEntity.getPaid(),
                        ticketEntity.getPaymentTime()
                );
            } else {
                throw new ResponseStatusException(BAD_REQUEST, INCORRECT_BOOKED_AND_PAID_FIELDS);
            }
        }

        throw new ResponseStatusException(BAD_REQUEST, INVALID_ID);
    }

    public TicketResDTO getTicket(Long id) {
        var ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isPresent()) {
            var ticketEntity = ticketOptional.get();

            return new TicketResDTO(
                    ticketEntity.getId(),
                    ticketEntity.getEvent().getName(),
                    ticketEntity.getPrice(),
                    ticketEntity.getBooked(),
                    ticketEntity.getBookingTime(),
                    ticketEntity.getPaid(),
                    ticketEntity.getPaymentTime()
            );
        }

        throw new ResponseStatusException(NOT_FOUND);
    }

    public List<TicketResDTO> getAllTickets() {
        var user = userService.getUser(SecurityContextHolder.getContext());

        return user.getTickets()
                .stream()
                .map(ticketEntity -> new TicketResDTO(
                        ticketEntity.getId(),
                        ticketEntity.getEvent().getName(),
                        ticketEntity.getPrice(),
                        ticketEntity.getBooked(),
                        ticketEntity.getBookingTime(),
                        ticketEntity.getPaid(),
                        ticketEntity.getPaymentTime()
                )).toList();
    }
}

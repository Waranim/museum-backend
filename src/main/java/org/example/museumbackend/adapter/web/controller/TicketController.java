package org.example.museumbackend.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.TicketReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.TicketResDTO;
import org.example.museumbackend.service.TicketService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Билеты", description = "Эндпоинты для взаимодействия с билетами. В будущем вся логика может быть переделана.")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TicketController {

    TicketService ticketService;

    @Operation(
            summary = "Создание билета"
    )
    @PostMapping("/api/ticket")
    @ResponseStatus(CREATED)
    public TicketResDTO createTicket(@Validated @RequestBody TicketReqDTO ticket) {
        return ticketService.createTicket(ticket);
    }

    @Operation(
            summary = "Получение информации о билете по идентификатору"
    )
    @GetMapping("/api/ticket/{id}")
    public TicketResDTO getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }

    @Operation(
            summary = "Получение всех билетов пользователя"
    )
    @GetMapping("/api/ticket")
    public List<TicketResDTO> getTickets() {
        return ticketService.getAllTickets();
    }

    @Operation(
            summary = "Получение всех билетов на мероприятие, доступно только гиду"
    )
    @GetMapping("/guide/api/ticket/{id}")
    public List<TicketResDTO> getTicketsByEvent(@PathVariable Long id) {
        return ticketService.getAllTicketsForEvent(id);
    }

    @Operation(
            summary = "Получение всех билетов на мероприятие, доступно только администратору"
    )
    @GetMapping("/guide/api/ticket/{id}")
    public List<TicketResDTO> getTicketsByUser(@PathVariable Long id) {
        return ticketService.getAllTickets(id);
    }
}

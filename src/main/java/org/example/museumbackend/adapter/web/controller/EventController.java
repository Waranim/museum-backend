package org.example.museumbackend.adapter.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.EventCreateDTO;
import org.example.museumbackend.adapter.web.DTO.request.EventReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.EventResDTO;
import org.example.museumbackend.service.EventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class EventController {

    EventService eventService;

    @GetMapping("/api/event/{id}")
    public EventResDTO getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @GetMapping("/api/events")
    public List<EventResDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @SneakyThrows
    @PostMapping("/marketer/api/event")
    @ResponseStatus(CREATED)
    public void createEvent(@Validated @RequestPart("event") EventCreateDTO eventCreateDTO,
                            @RequestPart("images") MultipartFile[] images) {
        eventService.createEvent(eventCreateDTO, images);
    }

    @PutMapping("/marketer/api/event/{id}")
    public void updateEvent(@PathVariable Long id, @RequestBody EventReqDTO eventReqDTO) {
        eventService.updateEvent(id, eventReqDTO);
    }

    @DeleteMapping("/marketer/api/event/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}

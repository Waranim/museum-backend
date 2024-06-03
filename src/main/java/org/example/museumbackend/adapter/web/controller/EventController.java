package org.example.museumbackend.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.EventCreateDTO;
import org.example.museumbackend.adapter.web.DTO.request.EventReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.EventLogoResDTO;
import org.example.museumbackend.adapter.web.DTO.response.EventResDTO;
import org.example.museumbackend.service.EventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Мероприятия", description = "Эндпоинты для взаимодействия с мероприятиями")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class EventController {

    EventService eventService;

    @Operation(
            summary = "Получение информации о мероприятии по идентификатору"
    )
    @GetMapping("/api/event/{id}")
    public EventResDTO getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @Operation(
            summary = "Получение полной информации по всем мероприятиям(Возможны существенные изменения см. описание)",
            description = "Данный эндпоинт может быть удалён в будущем или переписан"
    )
    @GetMapping("/api/events/info")
    public List<EventResDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @Operation(
            summary = "Получение краткой информации по всем мероприятиям",
            description = "В краткой информации о каждом мероприятии будет указано только: название, краткая информация," +
                    " описание, адрес и ссылка на одно изображение"
    )
    @GetMapping("/api/events")
    public List<EventLogoResDTO> getEventsLogo() {
        return eventService.getAllEventsLogo();
    }

    @Operation(
            summary = "Создание мероприятий, доступно только маркетологу и администратору",
            description = "ВАЖНО: в отличии от большинства методов, где используется json в теле запроса, в этом " +
                    "эндпоинте принимается multipart/from-data, где event является json, а images - файлы изображений"
    )
    @SneakyThrows
    @PostMapping("/marketer/api/event")
    @ResponseStatus(CREATED)
    public void createEvent(@Validated @RequestPart("event") EventCreateDTO eventCreateDTO,
                            @RequestPart("images") MultipartFile[] images) {
        eventService.createEvent(eventCreateDTO, images);
    }

    @Operation(
            summary = "Редактирование информации о мероприятии, доступно только маркетологу и администратору(Возможны существенные изменения см. описание)",
            description = "Для редактирования необходимо отправить идентификатор и параметр, который надо обновить." +
                    " В будущем возможно надо будет отправлять полностью всю информацию по мероприятию, которая должна быть " +
                    "в отредактированном мероприятии"
    )
    @PutMapping("/marketer/api/event/{id}")
    public void updateEvent(@PathVariable Long id, @RequestBody EventReqDTO eventReqDTO) {
        eventService.updateEvent(id, eventReqDTO);
    }

    @Operation(
            summary = "Удаление мероприятия по идентификатору"
    )
    @DeleteMapping("/marketer/api/event/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}

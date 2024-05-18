package org.example.museumbackend.adapter.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.TypeOfEventReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.TypeOfEventResDTO;
import org.example.museumbackend.service.TypeOfEventService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TypeOfEventController {

    TypeOfEventService typeOfEventService;

    @GetMapping("/api/type-of-event/{id}")
    public TypeOfEventResDTO getTypeOfEvent(@PathVariable Long id) {
        return typeOfEventService.getTypeOfEvent(id);
    }

    @GetMapping("/api/types-of-event")
    public List<TypeOfEventResDTO> getTypesOfEvent() {
        return typeOfEventService.getTypesOfEvent();
    }

    @PostMapping("/admin/api/type-of-event")
    @ResponseStatus(HttpStatus.CREATED)
    public TypeOfEventResDTO createTypeOfEvent(@Validated @RequestBody TypeOfEventReqDTO typeOfEventDTO) {
        return typeOfEventService.createTypeOfEvent(typeOfEventDTO);
    }

    @DeleteMapping("/admin/api/type-of-event/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteTypeOfEvent(@PathVariable Long id) {
        typeOfEventService.deleteTypeOfEvent(id);
    }
}

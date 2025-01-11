package org.example.museumbackend.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.SiteReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.SiteResDTO;
import org.example.museumbackend.service.SiteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Площадки", description = "Эндпоинты для взаимодействия с площадками проведения меропириятий")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SiteController {

    SiteService siteService;

    @Operation(
            summary = "Получение информации о площадке по идентификатору"
    )
    @GetMapping("/api/site/{id}")
    public SiteResDTO getSite(@PathVariable Long id) {
        return siteService.getSite(id);
    }

    @Operation(
            summary = "Получение информации о всех площадках"
    )
    @GetMapping("/api/sites")
    public List<SiteResDTO> getSites() {
        return siteService.getAllSites();
    }

    @Operation(
            summary = "Создание площадки, доступно только администратору"
    )
    @PostMapping("/admin/api/site")
    @ResponseStatus(CREATED)
    public SiteResDTO createSite(@Validated @RequestBody SiteReqDTO siteDTO) {
        return siteService.createSite(siteDTO);
    }

    @Operation(
            summary = "Удаление площадки по идентификатору, доступно только администратору"
    )
    @DeleteMapping("/admin/api/site/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
    }
}

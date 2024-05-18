package org.example.museumbackend.adapter.web.controller;

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

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SiteController {

    SiteService siteService;

    @GetMapping("/api/site/{id}")
    public SiteResDTO getSite(@PathVariable Long id) {
        return siteService.getSite(id);
    }

    @GetMapping("/api/sites")
    public List<SiteResDTO> getSites() {
        return siteService.getAllSites();
    }

    @PostMapping("/admin/api/site")
    @ResponseStatus(CREATED)
    public SiteResDTO createSite(@Validated @RequestBody SiteReqDTO siteDTO) {
        return siteService.createSite(siteDTO);
    }

    @DeleteMapping("/admin/api/site/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
    }
}

package org.example.museumbackend.adapter.web.DTO.response;

public record SiteResDTO(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude
) {
}

package org.example.museumbackend.adapter.web.DTO.response;

public record EventLogoResDTO(Long id, String name, String summary, String description, String address, ImageLinkDTO image) {
}

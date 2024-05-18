package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SiteReqDTO(Long id,
                         @NotNull
                      @NotEmpty
                      String name,
                         @NotNull
                      @NotEmpty
                      String address,
                         Double latitude,
                         Double longitude) {
    public SiteReqDTO {
        if (latitude == null) {
            latitude = 0.0;
        }
        if (longitude == null) {
            longitude = 0.0;
        }
    }
}

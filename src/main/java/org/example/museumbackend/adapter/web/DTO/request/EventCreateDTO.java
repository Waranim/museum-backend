package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EventCreateDTO(
        @NotNull
        Long siteId,
        @NotNull
        Long typeOfEventId,
        @NotNull
        @NotEmpty
        String date,
        @NotNull
        Integer age,
        Boolean adult,
        Boolean teenagers,
        Boolean kids,
        Boolean hia,
        @NotNull
        @NotEmpty
        String description,
        String kassir,
        @NotNull
        @NotEmpty
        PriceReqDTO[] prices
) {
        public EventCreateDTO {
                if (adult == null) {
                        adult = true;
                }
                if (teenagers == null) {
                        teenagers = true;
                }
                if (kids == null) {
                        kids = true;
                }
                if (hia == null) {
                        hia = false;
                }
        }
}

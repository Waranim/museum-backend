package org.example.museumbackend.adapter.web.DTO.request;

public record EventFilterDTO(
        Long siteId,
        Long typeOfEventId,
        String date,
        Integer age,
        Boolean adult,
        Boolean teenagers,
        Boolean kids,
        Boolean hia,
        Boolean bookingAllowed,
        Integer minPrice,
        Integer maxPrice
) {
}

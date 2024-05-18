package org.example.museumbackend.adapter.web.DTO.request;

public record EventReqDTO(
        Long siteId,
        Long typeOfEventId,
        String date,
        Integer age,
        Boolean adult,
        Boolean teenagers,
        Boolean kids,
        Boolean hia,
        String description,
        String kassir,
        Boolean completed
) {
}

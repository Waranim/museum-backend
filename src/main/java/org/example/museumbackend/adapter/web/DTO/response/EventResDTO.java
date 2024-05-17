package org.example.museumbackend.adapter.web.DTO.response;

import java.sql.Timestamp;
import java.util.List;

public record EventResDTO(
        Long id,
        Long siteId,
        Long typeOfEventId,
        Timestamp date,
        Integer age,
        Boolean adult,
        Boolean teenagers,
        Boolean kids,
        Boolean hia,
        String description,
        String kassir,
        Boolean completed,
        List<PriceResDTO> prices,
        List<ImageLinkDTO> images
) {
}

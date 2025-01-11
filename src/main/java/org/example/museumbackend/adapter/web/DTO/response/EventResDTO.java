package org.example.museumbackend.adapter.web.DTO.response;

import java.util.List;

public record EventResDTO(
        Long id,
        String name,
        String summary,
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
        Long viewCount,
        Boolean completed,
        List<PriceResDTO> prices,
        List<ImageLinkDTO> images
) {
}

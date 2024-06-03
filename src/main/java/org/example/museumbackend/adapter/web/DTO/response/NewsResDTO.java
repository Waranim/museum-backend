package org.example.museumbackend.adapter.web.DTO.response;

import java.sql.Timestamp;
import java.util.List;

public record NewsResDTO(
        Long id,
        String title,
        String summary,
        String content,
        Timestamp publishDate,
        List<ImageLinkDTO> images
) {
}

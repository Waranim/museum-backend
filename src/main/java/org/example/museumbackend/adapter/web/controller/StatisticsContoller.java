package org.example.museumbackend.adapter.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.EventStatisticsReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.EventStatisticsDTO;
import org.example.museumbackend.service.EventStatisticsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "Статистика", description = "Эндпоинт для взаимодействия с статистикой")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class StatisticsContoller {

    EventStatisticsService eventStatisticsService;

    @PostMapping("/pr/api/event/filter")
    public List<EventStatisticsDTO> getEventStatistics(EventStatisticsReqDTO reqDTO) {
        return eventStatisticsService.getEventStatistics(reqDTO);
    }
}

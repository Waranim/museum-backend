package org.example.museumbackend.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.web.DTO.request.NewsReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.NewsResDTO;
import org.example.museumbackend.service.NewsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Новости", description = "Эндпоинты для взаимодействия с новостями")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class NewsController {

    NewsService newsService;

    @Operation(
            summary = "Создание новости, доступно только пиару и администратору",
            description = "ВАЖНО: в отличии от большинства методов, где используется json в теле запроса, в этом " +
                    "эндпоинте принимается multipart/from-data, где news является json, а images - файлы изображений"
    )
    @SneakyThrows
    @PostMapping("/pr/api/news")
    @ResponseStatus(CREATED)
    public NewsResDTO createNews(@Validated @RequestPart("news") NewsReqDTO news,
                                 @RequestPart("images")MultipartFile[] images) {
        return newsService.createNews(news, images);
    }

    @Operation(
            summary = "Получение информации о новости по идентификатору"
    )
    @GetMapping("api/news/{id}")
    public NewsResDTO getNews(@PathVariable Long id) {
        return newsService.getNews(id);
    }

    @Operation(
            summary = "Получение информации по всем новостям(Возможны существенные изменения см. описание)",
            description = "Данный эндпоинт может быть удалён в будущем или переписан"
    )
    @GetMapping("/api/news")
    public List<NewsResDTO> getAllNews() {
        return newsService.getAllNews();
    }

    @Operation(
            summary = "Обновление новости по идентификатору, доступно только пиару и администратору",
            description = "Необходимо указывать всю информацию о новости с учётом данных, которые следует обновить"
    )
    @PutMapping("/pr/api/news/{id}")
    public NewsResDTO updateNews(@PathVariable Long id, @Validated @RequestBody NewsReqDTO news) {
        return newsService.updateNews(id, news);
    }

    @Operation(
            summary = "Удаление новости по идентификатору, доступно только пиару и администратору"
    )
    @DeleteMapping("/pr/api/news/{id}")
    @ResponseStatus(NO_CONTENT)
    public void removeNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}

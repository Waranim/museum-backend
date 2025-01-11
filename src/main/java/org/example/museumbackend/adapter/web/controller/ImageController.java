package org.example.museumbackend.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Изображения", description = "Эндпоинты для взаимодействия с изображениями")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ImageController {

    ImageService imageService;

    @Operation(
            summary = "Получение изображения по идентификатору"
    )
    @GetMapping("/api/images/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        return imageService.getImage(id);
    }

    @Operation(
            summary = "Удаление изображения по идентификатору, доступно только маркетологу и администратору"
    )
    @DeleteMapping("/marketer/api/image/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
    }
}

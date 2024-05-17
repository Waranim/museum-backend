package org.example.museumbackend.adapter.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ImageController {

    ImageService imageService;

    @GetMapping("/api/images/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        return imageService.getImage(id);
    }

    @DeleteMapping("/marketer/api/image/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
    }
}

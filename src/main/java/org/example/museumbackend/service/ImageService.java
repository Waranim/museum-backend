package org.example.museumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.ImageRepository;
import org.example.museumbackend.domain.ImageEntity;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Paths;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ImageService {

    private static final String UNSUPPORTED_IMAGE_TYPE = "Unsupported image type: ";
    private static final String JPEG = "image/jpeg";
    private static final String PNG = "image/png";
    ImageRepository imageRepository;

    @SneakyThrows
    public ResponseEntity<Resource> getImage(Long id) {
        ImageEntity image;
        var imageEntityOptional = imageRepository.findById(id);
        if (imageEntityOptional.isPresent()) {
            image = imageEntityOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var path = Paths.get(image.getPath());
        var imageResource = new PathResource(path);
        var imageContentType = Files.probeContentType(path);
        MediaType contentType;
        if (imageContentType.equals(PNG)) {
            contentType = MediaType.IMAGE_PNG;
        } else if (imageContentType.equals(JPEG)) {
            contentType = MediaType.IMAGE_JPEG;
        } else {
            throw new RuntimeException(UNSUPPORTED_IMAGE_TYPE + imageContentType);
        }

        if (imageResource.exists() || imageResource.isReadable()) {
            return ResponseEntity.ok().contentType(contentType).body(imageResource);
        }

        return ResponseEntity.notFound().build();
    }

    @SneakyThrows
    public void deleteImage(Long id) {
        ImageEntity image;
        var imageEntityOptional = imageRepository.findById(id);
        if (imageEntityOptional.isPresent()) {
            image = imageEntityOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var path = Paths.get(image.getPath());
        Files.delete(path);

        imageRepository.deleteById(id);
    }
}

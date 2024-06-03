package org.example.museumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.ImageRepository;
import org.example.museumbackend.adapter.web.DTO.response.ImageLinkDTO;
import org.example.museumbackend.domain.EventEntity;
import org.example.museumbackend.domain.ImageEntity;
import org.example.museumbackend.domain.NewsEntity;
import org.example.museumbackend.domain.common.BaseDomainEntity;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ImageService {

    private static final String UNSUPPORTED_IMAGE_TYPE = "Unsupported image type: ";
    private static final String JPEG = "image/jpeg";
    private static final String PNG = "image/png";
    private static final String IMAGE_PATH = "/api/images/";
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

    public List<ImageEntity> createListImages(BaseDomainEntity baseEntity, MultipartFile[] images) throws Exception {
        EventEntity eventEntity = null;
        NewsEntity newsEntity = null;
        if (baseEntity instanceof EventEntity) {
            eventEntity = (EventEntity) baseEntity;
        } else if (baseEntity instanceof NewsEntity) {
            newsEntity = (NewsEntity) baseEntity;
        } else {
            throw new ResponseStatusException(BAD_REQUEST);
        }

        var imageList = new ArrayList<ImageEntity>();
        for (MultipartFile image : images) {
            Path path = Paths.get("images/" + image.getOriginalFilename());
            try {
                if (!Files.exists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }

                Files.write(path, image.getBytes());
            } catch (Exception e) {
                throw new Exception(e);
            }

            if (eventEntity != null) {
                imageList.add(new ImageEntity(path.toString(), eventEntity));
            } else {
                imageList.add(new ImageEntity(path.toString(), newsEntity));
            }
        }

        return imageList;
    }

    public List<ImageLinkDTO> createListImageLinks(List<ImageEntity> imageEntities) {
        return imageEntities
                .stream()
                .map(imageEntity -> new ImageLinkDTO(IMAGE_PATH + imageEntity.getId()))
                .toList();
    }
}

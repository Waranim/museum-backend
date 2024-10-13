package org.example.museumbackend.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.*;
import org.example.museumbackend.adapter.web.DTO.request.EventCreateDTO;
import org.example.museumbackend.adapter.web.DTO.request.EventReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.EventLogoResDTO;
import org.example.museumbackend.adapter.web.DTO.response.EventResDTO;
import org.example.museumbackend.adapter.web.DTO.response.ImageLinkDTO;
import org.example.museumbackend.adapter.web.DTO.response.PriceResDTO;
import org.example.museumbackend.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventService {

    private static final String INCORRECT_ID_SPECIFIED_MESSAGE = "Incorrect id specified on object: ";
    private static final String TYPE_OF_EVENT = "type of event";
    private static final String SITE = "site";
    private static final String EVENT = "event";
    private static final String IMAGE_PATH = "/api/images/";

    EventRepository eventRepository;
    SiteRepository siteRepository;
    TypeOfEventRepository typeOfEventRepository;
    ImageService imageService;

    public void createEvent(EventCreateDTO eventDTO, MultipartFile[] images) throws Exception {
        SiteEntity site;
        TypeOfEventEntity typeOfEvent;
        var event = new EventEntity();

        var prices = Arrays.stream(eventDTO.prices())
                .map(priceDTO -> new PriceEntity(priceDTO.price(), priceDTO.age(), event))
                .toList();

        var dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        var date = new Timestamp(dateFormat.parse(eventDTO.date()).getTime());
        var duration = Duration.ofDays(eventDTO.bookingTime().days()).plusHours(eventDTO.bookingTime().hours()).plusMinutes(eventDTO.bookingTime().minutes());
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

            var imageEntity = new ImageEntity(path.toString(), event);

            imageList.add(imageEntity);
        }

        var siteOptional = siteRepository.findById(eventDTO.siteId());
        var typeOfEventOptional = typeOfEventRepository.findById(eventDTO.typeOfEventId());

        if (siteOptional.isPresent() && typeOfEventOptional.isPresent()) {
            site = siteOptional.get();
            typeOfEvent = typeOfEventOptional.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, INCORRECT_ID_SPECIFIED_MESSAGE + SITE + " or/and " + TYPE_OF_EVENT);
        }

        event.setName(eventDTO.name());
        event.setSummary(eventDTO.summary());
        event.setSite(site);
        event.setTypeOfEvent(typeOfEvent);
        event.setDate(date);
        event.setAge(eventDTO.age());
        event.setAdult(eventDTO.adult());
        event.setTeenagers(eventDTO.teenagers());
        event.setKids(eventDTO.kids());
        event.setHia(eventDTO.hia());
        event.setDescription(eventDTO.description());
        event.setBookingAllowed(eventDTO.bookingAllowed() != null ? eventDTO.bookingAllowed() : false);
        event.setBookingTime(duration.toString());
        event.setDuration(eventDTO.duration());
        event.setKassir(eventDTO.kassir());
        event.setViewCount(0L);
        event.setPrices(prices);
        event.setImages(imageList);
        event.setCompleted(false);

        eventRepository.save(event);
    }

    public EventResDTO getEvent(Long id) {
        EventEntity event;
        var eventEntity = eventRepository.findById(id);
        if (eventEntity.isPresent()) {
            event = eventEntity.get();
            var viewCount = event.getViewCount();
            viewCount++;
            event.setViewCount(viewCount);
            eventRepository.save(event);
        } else {
            return null;
        }

        var pricesDTO = event.getPrices()
                .stream()
                .map(price -> new PriceResDTO(price.getId(), price.getPrice(), price.getAge()))
                .toList();

        var imagesDTO = event.getImages()
                .stream()
                .map(imageEntity -> new ImageLinkDTO(IMAGE_PATH + imageEntity.getId()))
                .toList();

        return new EventResDTO(
                event.getId(),
                event.getName(),
                event.getSummary(),
                event.getSite().getId(),
                event.getTypeOfEvent().getId(),
                event.getDate(),
                event.getAge(),
                event.getAdult(),
                event.getTeenagers(),
                event.getKids(),
                event.getHia(),
                event.getDescription(),
                event.getKassir(),
                event.getViewCount(),
                event.getCompleted(),
                pricesDTO,
                imagesDTO
        );
    }

    public List<EventResDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(event -> getEvent(event.getId())).toList();
    }

    public List<EventLogoResDTO> getAllEventsLogo() {
        return eventRepository
                .findAll()
                .stream()
                .filter(event -> !event.getCompleted())
                .map(event -> new EventLogoResDTO(
                        event.getId(),
                        event.getName(),
                        event.getSummary(),
                        event.getDescription(),
                        event.getSite().getAddress(),
                        event.getImages().isEmpty() ? null : new ImageLinkDTO(IMAGE_PATH + event.getImages().getFirst().getId())))
                .toList();
    }

    public void updateEvent(Long id, EventReqDTO eventDTO) {
        EventEntity event;
        var eventOptional = eventRepository.findById(id);

        if (eventOptional.isPresent()) {
            event = eventOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INCORRECT_ID_SPECIFIED_MESSAGE + EVENT);
        }

        replaceDataEvent(eventDTO, event);
    }

    @SneakyThrows
    private void replaceDataEvent(EventReqDTO eventDTO, EventEntity event) {
        if (eventDTO.date() != null) {
            var dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            event.setDate(new Timestamp(dateFormat.parse(eventDTO.date()).getTime()));
        }

        if (eventDTO.siteId() != null) {
            var siteOptional = siteRepository.findById(eventDTO.siteId());
            if (siteOptional.isPresent()) {
                event.setSite(siteOptional.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INCORRECT_ID_SPECIFIED_MESSAGE + SITE);
            }
        }

        if (eventDTO.typeOfEventId() != null) {
            var typeOfEventOptional = typeOfEventRepository.findById(eventDTO.typeOfEventId());
            if (typeOfEventOptional.isPresent()) {
                event.setTypeOfEvent(typeOfEventOptional.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INCORRECT_ID_SPECIFIED_MESSAGE + TYPE_OF_EVENT);
            }
        }

        Optional.ofNullable(eventDTO.name()).ifPresent(event::setName);
        Optional.ofNullable(eventDTO.summary()).ifPresent(event::setSummary);
        Optional.ofNullable(eventDTO.age()).ifPresent(event::setAge);
        Optional.ofNullable(eventDTO.adult()).ifPresent(event::setAdult);
        Optional.ofNullable(eventDTO.teenagers()).ifPresent(event::setTeenagers);
        Optional.ofNullable(eventDTO.kids()).ifPresent(event::setKids);
        Optional.ofNullable(eventDTO.hia()).ifPresent(event::setHia);
        Optional.ofNullable(eventDTO.description()).ifPresent(event::setDescription);
        Optional.ofNullable(eventDTO.kassir()).ifPresent(event::setKassir);
        Optional.ofNullable(eventDTO.completed()).ifPresent(event::setCompleted);

        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        EventEntity event;
        var eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            event = eventOptional.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, INCORRECT_ID_SPECIFIED_MESSAGE + EVENT);
        }

        event.getImages().forEach(imageEntity -> imageService.deleteImage(imageEntity.getId()));

        eventRepository.deleteById(id);
    }
}

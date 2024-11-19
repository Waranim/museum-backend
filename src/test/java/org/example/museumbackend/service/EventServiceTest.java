package org.example.museumbackend.service;

import org.example.museumbackend.adapter.repository.EventRepository;
import org.example.museumbackend.adapter.repository.SiteRepository;
import org.example.museumbackend.adapter.repository.TypeOfEventRepository;
import org.example.museumbackend.adapter.web.DTO.request.BookingTimeDTO;
import org.example.museumbackend.adapter.web.DTO.request.EventCreateDTO;
import org.example.museumbackend.adapter.web.DTO.request.EventReqDTO;
import org.example.museumbackend.adapter.web.DTO.request.PriceReqDTO;
import org.example.museumbackend.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private SiteRepository siteRepository;
    @Mock
    private TypeOfEventRepository typeOfEventRepository;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_shouldCreateEventSuccessfully() throws Exception {
        var siteEntity = new SiteEntity();
        var typeOfEventEntity = new TypeOfEventEntity();

        when(siteRepository.findById(1L)).thenReturn(Optional.of(siteEntity));
        when(typeOfEventRepository.findById(2L)).thenReturn(Optional.of(typeOfEventEntity));

        var eventCreateDTO = new EventCreateDTO(
                "Event Name",
                "Event Summary",
                1L,
                2L,
                "01-12-2024 10:00",
                18,
                true,
                true,
                false,
                false,
                "Event Description",
                true,
                new BookingTimeDTO(1, 2, 30),
                120,
                "Kassir",
                new PriceReqDTO[]{new PriceReqDTO(100, 18)}
        );

        MultipartFile[] images = new MultipartFile[0];

        eventService.createEvent(eventCreateDTO, images);

        ArgumentCaptor<EventEntity> captor = ArgumentCaptor.forClass(EventEntity.class);
        verify(eventRepository).save(captor.capture());

        var savedEvent = captor.getValue();
        assertNotNull(savedEvent);
        assertEquals("Event Name", savedEvent.getName());
        assertEquals(siteEntity, savedEvent.getSite());
        assertEquals(typeOfEventEntity, savedEvent.getTypeOfEvent());
        assertEquals(1, savedEvent.getPrices().size());
        assertEquals(100, savedEvent.getPrices().getFirst().getPrice());
    }

    @Test
    void getEvent_shouldReturnEventWithPricesAndImages() {
        var eventEntity = mock(EventEntity.class);
        var priceEntity = mock(PriceEntity.class);
        var imageEntity = mock(ImageEntity.class);

        when(eventEntity.getId()).thenReturn(1L);
        when(eventEntity.getName()).thenReturn("Event Name");
        when(eventEntity.getSummary()).thenReturn("Summary");
        when(eventEntity.getDate()).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(eventEntity.getViewCount()).thenReturn(0L);

        when(eventEntity.getPrices()).thenReturn(List.of(priceEntity));
        when(eventEntity.getImages()).thenReturn(List.of(imageEntity));

        when(priceEntity.getId()).thenReturn(1L);
        when(priceEntity.getPrice()).thenReturn(100);
        when(priceEntity.getAge()).thenReturn(18);

        when(imageEntity.getId()).thenReturn(10L);

        var siteEntity = mock(SiteEntity.class);
        when(siteEntity.getId()).thenReturn(2L);
        when(eventEntity.getSite()).thenReturn(siteEntity);

        var typeOfEventEntity = mock(TypeOfEventEntity.class);
        when(typeOfEventEntity.getId()).thenReturn(3L);
        when(eventEntity.getTypeOfEvent()).thenReturn(typeOfEventEntity);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(eventEntity));

        var result = eventService.getEvent(1L, false);

        assertNotNull(result);
        assertEquals("Event Name", result.name());
        assertEquals(1, result.prices().size());
        assertEquals(100, result.prices().getFirst().price());
        assertEquals(1, result.images().size());
        assertEquals("/api/images/10", result.images().getFirst().link());
        assertEquals(1L, result.id());

        verify(eventRepository).save(eventEntity);
        verify(eventEntity).setViewCount(1L);
    }

    @Test
    void updateEvent_shouldUpdateEventSuccessfully() {
        var eventEntity = new EventEntity();
        eventEntity.setName("Old Name");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(eventEntity));

        var eventReqDTO = new EventReqDTO(
                "New Name",
                "Updated Summary",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        eventService.updateEvent(1L, eventReqDTO);

        assertEquals("New Name", eventEntity.getName());
        verify(eventRepository).save(eventEntity);
    }

    @Test
    void deleteEvent_shouldDeleteEventWithImages() {
        var eventEntity = mock(EventEntity.class);
        var imageEntity1 = mock(ImageEntity.class);
        var imageEntity2 = mock(ImageEntity.class);

        when(eventEntity.getImages()).thenReturn(List.of(imageEntity1, imageEntity2));

        when(imageEntity1.getId()).thenReturn(10L);
        when(imageEntity2.getId()).thenReturn(11L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(eventEntity));

        eventService.deleteEvent(1L);

        verify(eventRepository).deleteById(1L);
        verify(imageService).deleteImage(10L);
        verify(imageService).deleteImage(11L);
    }
}
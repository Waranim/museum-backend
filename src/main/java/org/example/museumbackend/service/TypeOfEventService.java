package org.example.museumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.TypeOfEventRepository;
import org.example.museumbackend.adapter.web.DTO.request.TypeOfEventDTO;
import org.example.museumbackend.adapter.web.DTO.response.TypeOfEventResDTO;
import org.example.museumbackend.domain.TypeOfEventEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class TypeOfEventService {

    TypeOfEventRepository typeOfEventRepository;

    public TypeOfEventResDTO createTypeOfEvent(TypeOfEventDTO typeOfEventDTO) {
        var typeOfEvent = new TypeOfEventEntity(typeOfEventDTO.name());
        typeOfEventRepository.save(typeOfEvent);

        return new TypeOfEventResDTO(typeOfEvent.getId(), typeOfEvent.getName());
    }

    public TypeOfEventResDTO getTypeOfEvent(Long id) {
        TypeOfEventEntity typeOfEvent;
        var typeOfEventOptional = typeOfEventRepository.findById(id);
        if (typeOfEventOptional.isPresent()) {
            typeOfEvent = typeOfEventOptional.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND);
        }

        return new TypeOfEventResDTO(typeOfEvent.getId(), typeOfEvent.getName());
    }

    public List<TypeOfEventResDTO> getTypesOfEvent() {
        return typeOfEventRepository
                .findAll()
                .stream()
                .map(typeOfEventEntity -> new TypeOfEventResDTO(
                        typeOfEventEntity.getId(),
                        typeOfEventEntity.getName()))
                .toList();
    }

    public void deleteTypeOfEvent(Long id) {
        var typeOfEventOptional = typeOfEventRepository.findById(id);
        if (typeOfEventOptional.isPresent()) {
            typeOfEventRepository.delete(typeOfEventOptional.get());
        } else {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}

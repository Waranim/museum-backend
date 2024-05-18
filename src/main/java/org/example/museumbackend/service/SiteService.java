package org.example.museumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.SiteRepository;
import org.example.museumbackend.adapter.web.DTO.request.SiteReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.SiteResDTO;
import org.example.museumbackend.domain.SiteEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SiteService {

    SiteRepository siteRepository;

    public SiteResDTO createSite(SiteReqDTO siteDTO) {
        var site = new SiteEntity(siteDTO.name(), siteDTO.address(), siteDTO.latitude(), siteDTO.longitude());
        siteRepository.save(site);

        return new SiteResDTO(
                site.getId(),
                site.getName(),
                site.getAddress(),
                site.getLatitude(),
                site.getLongitude());
    }

    public SiteResDTO getSite(Long id) {
        var siteOptional = siteRepository.findById(id);
        if (siteOptional.isPresent()) {
            var site = siteOptional.get();
            return new SiteResDTO(
                    site.getId(),
                    site.getName(),
                    site.getAddress(),
                    site.getLatitude(),
                    site.getLongitude());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public List<SiteResDTO> getAllSites() {

        return siteRepository
                .findAll()
                .stream()
                .map(site -> new SiteResDTO(
                        site.getId(),
                        site.getName(),
                        site.getAddress(),
                        site.getLatitude(),
                        site.getLongitude()))
                .toList();
    }

    public void deleteSite(Long id) {
        var siteOptional = siteRepository.findById(id);
        if (siteOptional.isPresent()) {
            siteRepository.delete(siteOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

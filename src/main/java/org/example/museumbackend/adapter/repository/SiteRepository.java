package org.example.museumbackend.adapter.repository;

import org.example.museumbackend.domain.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<SiteEntity, Long> {
    List<SiteEntity> findByName(String name);
}

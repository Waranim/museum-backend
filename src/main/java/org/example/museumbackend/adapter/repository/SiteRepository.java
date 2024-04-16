package org.example.museumbackend.adapter.repository;

import org.example.museumbackend.domain.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<SiteEntity, Long> {
}

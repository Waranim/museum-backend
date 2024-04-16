package org.example.museumbackend.adapter.repository;

import org.example.museumbackend.domain.TypeOfEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfEventRepository extends JpaRepository<TypeOfEventEntity, Long> {
}

package org.example.museumbackend.adapter.repository;

import org.example.museumbackend.domain.TypeOfEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfEventRepository extends JpaRepository<TypeOfEventEntity, Long> {
    List<TypeOfEventEntity> findByName(String name);
}

package org.example.museumbackend.adapter.repository;

import org.example.museumbackend.domain.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}

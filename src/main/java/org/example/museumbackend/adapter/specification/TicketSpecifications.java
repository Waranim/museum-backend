package org.example.museumbackend.adapter.specification;

import org.example.museumbackend.domain.TicketEntity;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecifications {

    public static Specification<TicketEntity> isBooked() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("booked"));
    }

    public static Specification<TicketEntity> isPaid() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("paid"));
    }
}

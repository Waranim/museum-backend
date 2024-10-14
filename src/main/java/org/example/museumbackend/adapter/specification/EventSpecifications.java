package org.example.museumbackend.adapter.specification;

import jakarta.persistence.criteria.Join;
import org.example.museumbackend.domain.EventEntity;
import org.example.museumbackend.domain.PriceEntity;
import org.springframework.data.jpa.domain.Specification;
import java.sql.Timestamp;

public class EventSpecifications {

    public static Specification<EventEntity> hasSite(Long siteId) {
        return (root, query, cb) -> cb.equal(root.get("site").get("id"), siteId);
    }

    public static Specification<EventEntity> hasType(Long typeId) {
        return (root, query, cb) -> cb.equal(root.get("typeOfEvent").get("id"), typeId);
    }

    public static Specification<EventEntity> hasDate(Timestamp date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    public static Specification<EventEntity> isForAdults() {
        return (root, query, cb) -> cb.isTrue(root.get("adult"));
    }

    public static Specification<EventEntity> isForTeenagers() {
        return (root, query, cb) -> cb.isTrue(root.get("teenagers"));
    }

    public static Specification<EventEntity> isForKids() {
        return (root, query, cb) -> cb.isTrue(root.get("kids"));
    }

    public static Specification<EventEntity> isHIA() {
        return (root, query, cb) -> cb.isTrue(root.get("hia"));
    }

    public static Specification<EventEntity> hasBookingAllowed() {
        return (root, query, cb) -> cb.isTrue(root.get("bookingAllowed"));
    }

    public static Specification<EventEntity> hasPriceRange(Integer minPrice, Integer maxPrice) {
        return (root, query, cb) -> {
            Join<EventEntity, PriceEntity> priceJoin = root.join("prices");
            return cb.between(priceJoin.get("price"), minPrice, maxPrice);
        };
    }
}


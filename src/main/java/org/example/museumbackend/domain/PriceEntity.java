package org.example.museumbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

import java.util.List;

@Entity
@Table(name = "price")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity extends BaseDomainEntity {

    private Integer price;

    private Integer age;

    @ManyToMany
    @JoinTable(
            name = "price_event",
            joinColumns = { @JoinColumn(name = "price_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    private List<EventEntity> events;
}

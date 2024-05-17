package org.example.museumbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

@Entity
@Table(name = "price")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity extends BaseDomainEntity {

    private Integer price;

    private Integer age;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;
}

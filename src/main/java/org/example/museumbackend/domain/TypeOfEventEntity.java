package org.example.museumbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

import java.util.List;

@Entity
@Table(name = "type_of_event")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfEventEntity extends BaseDomainEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "typeOfEvent")
    private List<EventEntity> events;
}

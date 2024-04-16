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
@Table(name = "site")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SiteEntity extends BaseDomainEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "site")
    private List<EventEntity> events;
}

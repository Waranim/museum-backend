package org.example.museumbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

import java.util.List;

@Entity
@Table(name = "event")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity extends BaseDomainEntity {

    @ManyToOne
    @JoinColumn(name = "site_id")
    private SiteEntity site;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeOfEventEntity typeOfEvent;

    private java.sql.Timestamp date;

    private Integer age;

    private Boolean adult;

    private Boolean teenagers;

    private Boolean kids;

    private Boolean hia;

    private String description;

    private String kassir;

    private Boolean completed;

    @ManyToMany(mappedBy = "events")
    private List<UserEntity> users;

    @ManyToMany(mappedBy = "events")
    private List<PriceEntity> prices;

    @OneToMany(mappedBy = "event")
    private List<ImageEntity> images;
}

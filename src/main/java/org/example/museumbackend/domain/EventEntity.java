package org.example.museumbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "event")
@Setter
@Getter
@NoArgsConstructor
public class EventEntity extends BaseDomainEntity {

    @ManyToOne
    @JoinColumn(name = "site_id")
    private SiteEntity site;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeOfEventEntity typeOfEvent;

    private Timestamp date;

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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<PriceEntity> prices;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
    private List<ImageEntity> images;
}

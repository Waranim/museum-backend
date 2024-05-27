package org.example.museumbackend.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

import java.util.List;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseDomainEntity {

    @Column(unique = true, nullable = false)
    private String username;

    private String email;

    @Column(name = "number_phone", length = 20)
    private String numberPhone;

    @Column(nullable = false)
    private Boolean hia;

    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    private List<EventEntity> events;

    @OneToMany(mappedBy = "user")
    private List<TicketEntity> tickets;
}

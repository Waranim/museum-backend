package org.example.museumbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

import java.sql.Timestamp;

@Entity
@Table(name = "ticket")
@Setter
@Getter
@NoArgsConstructor
public class TicketEntity extends BaseDomainEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    private Integer price;

    private Boolean booked;

    @Column(name = "booking_time")
    private Timestamp bookingTime;

    private Boolean paid;

    @Column(name = "payment_time")
    private Timestamp paymentTime;
}

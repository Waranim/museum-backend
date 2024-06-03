package org.example.museumbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

@Entity
@Table(name = "image")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity extends BaseDomainEntity {

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private NewsEntity news;

    public ImageEntity(String path, EventEntity event) {
        this.path = path;
        this.event = event;
    }

    public ImageEntity(String path, NewsEntity news) {
        this.path = path;
        this.news = news;
    }
}

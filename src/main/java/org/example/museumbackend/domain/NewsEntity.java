package org.example.museumbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.museumbackend.domain.common.BaseDomainEntity;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "news")
@Setter
@Getter
@NoArgsConstructor
public class NewsEntity extends BaseDomainEntity {

    private String title;

    private String summary;

    private String content;

    @Column(name = "publish_date")
    private Timestamp publishDate;

    @OneToMany(mappedBy = "news", cascade = CascadeType.PERSIST)
    private List<ImageEntity> images;
}

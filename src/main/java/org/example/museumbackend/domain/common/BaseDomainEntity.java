package org.example.museumbackend.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseDomainEntity {
    @Id
    @SequenceGenerator(name = "hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    protected Long id;

    @JsonIgnore
    @Version
    protected Integer version;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BaseDomainEntity)) {
            return false;
        }

        var other = (BaseDomainEntity) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 17;
    }
}

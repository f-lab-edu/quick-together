package com.flab.quicktogether.matching.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class AbleRole {

    @Column(name = "able_role")
    @Id @GeneratedValue
    Long id;

    @Embedded
    Role role;

    public AbleRole() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbleRole ableRole)) return false;
        return getId().equals(ableRole.getId()) && Objects.equals(getRole(), ableRole.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRole());
    }

    @Override
    public String toString() {
        return "AbleRole{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}

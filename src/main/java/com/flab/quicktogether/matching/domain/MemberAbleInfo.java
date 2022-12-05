package com.flab.quicktogether.matching.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class MemberAbleInfo {
    @Id @GeneratedValue
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Member member;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="memberAbleInfo")
    List<AbleRole> ableRoles = new ArrayList<>();


    //equals와 해시코드가 필요할까...?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberAbleInfo that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

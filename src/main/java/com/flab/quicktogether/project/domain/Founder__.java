package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Founder__ {

    @Id
    @GeneratedValue
    @Column(name = "founder_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private RoleType projectRole;

    public Founder__(Member member) {
        this.member = member;
    }

}

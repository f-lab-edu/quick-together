package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Founder {

    @Id
    @GeneratedValue
    @Column(name = "founder_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}

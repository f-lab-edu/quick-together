package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class Participant__ {

    @Id
    @GeneratedValue
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private RoleType projectRole;

}

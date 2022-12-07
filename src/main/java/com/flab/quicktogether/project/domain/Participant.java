package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.scheduling.domain.AvailableTimes;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Participant {

    @Id
    @GeneratedValue
    @Column(name = "founder_id")
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

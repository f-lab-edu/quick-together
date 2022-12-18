package com.flab.quicktogether.project.domain;


import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class RecruitPosition {

    @Id
    @GeneratedValue
    @Column(name = "recruitPosition_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Position position;

    public RecruitPosition(Project project, Position position) {
        this.project = project;
        this.position = position;
    }
}
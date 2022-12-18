package com.flab.quicktogether.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class RecruitSkillStack {

    @Id
    @GeneratedValue
    @Column(name = "recruitSkillStack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private SkillStack skillStack;

    public RecruitSkillStack(Project project, SkillStack skillStack) {
        this.project = project;
        this.skillStack = skillStack;
    }
}

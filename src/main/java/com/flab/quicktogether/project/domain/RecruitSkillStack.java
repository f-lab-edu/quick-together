package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.common.SkillStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

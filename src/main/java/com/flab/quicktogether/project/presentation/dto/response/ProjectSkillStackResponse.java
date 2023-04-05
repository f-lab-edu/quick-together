package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.common.SkillStack;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectSkillStackResponse {
    @NotNull
    private Long projectId;

    @NotNull
    private Set<SkillStack> skillStacks = new HashSet<>();

    public ProjectSkillStackResponse(Long projectId, Project project) {
        this.projectId = projectId;
        this.skillStacks = project.getSkillStacks();
    }

}

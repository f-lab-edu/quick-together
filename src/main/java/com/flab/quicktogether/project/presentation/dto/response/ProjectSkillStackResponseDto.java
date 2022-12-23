package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.domain.SkillStack;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectSkillStackResponseDto {
    @NotNull
    private Long projectId;

    @NotNull
    private List<SkillStack> skillStacks = new ArrayList<>();

    public ProjectSkillStackResponseDto(Long projectId, Project project) {
        this.projectId = projectId;
        this.skillStacks = project.getSkillStacks();
    }

}

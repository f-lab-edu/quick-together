package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.project.domain.Position;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.domain.SkillStack;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRecruitmentPositionsResponseDto {
    @NotNull
    private Long projectId;

    @NotNull
    private List<Position> RecruitmentPositions = new ArrayList<>();

    public ProjectRecruitmentPositionsResponseDto(Long projectId, Project project) {
        this.projectId = projectId;
        this.RecruitmentPositions = project.getRecruitmentPositions();
    }

}

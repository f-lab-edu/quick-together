package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.project.domain.Project;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRecruitmentPositionsResponse {
    @NotNull
    private Long projectId;

    @NotNull
    private Set<Position> RecruitmentPositions = new HashSet<>();

    public ProjectRecruitmentPositionsResponse(Long projectId, Project project) {
        this.projectId = projectId;
        this.RecruitmentPositions = project.getRecruitmentPositions();
    }

}

package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.project.domain.Position;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.domain.SkillStack;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRecruitmentPositionsResponseDto {
    @NonNull
    private Long projectId;

    @NonNull
    private List<Position> RecruitmentPositions = new ArrayList<>();

    public ProjectRecruitmentPositionsResponseDto(Long projectId, Project project) {
        this.projectId = projectId;
        this.RecruitmentPositions = project.getRecruitmentPositions();
    }

}

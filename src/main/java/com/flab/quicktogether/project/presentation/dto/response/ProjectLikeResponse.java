package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.project.domain.Project;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectLikeResponse {
    @NotNull
    private Long projectId;

    @NotNull
    private Long totalLikes;

}

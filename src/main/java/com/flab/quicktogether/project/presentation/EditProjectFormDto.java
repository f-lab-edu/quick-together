package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.domain.ProjectStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EditProjectFormDto {
    private String projectName;
    private String projectSummary;
    private String projectDescription;
    private LocalDateTime startDateTime;
    private Long periodDate;
    private ProjectStatus projectStatus;
}

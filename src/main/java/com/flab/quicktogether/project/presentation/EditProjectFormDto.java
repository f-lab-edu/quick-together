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

    public EditProjectFormDto(String projectName, String projectSummary, String projectDescription, LocalDateTime startDateTime, Long periodDate, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.projectSummary = projectSummary;
        this.projectDescription = projectDescription;
        this.startDateTime = startDateTime;
        this.periodDate = periodDate;
        this.projectStatus = projectStatus;
    }
}

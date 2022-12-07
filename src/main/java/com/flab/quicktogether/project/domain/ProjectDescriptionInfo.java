package com.flab.quicktogether.project.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
public class ProjectDescriptionInfo {

    @Lob
    private String projectSummary; // 프로젝트 간단 설명

    @Lob
    private String description; // 프로젝트 상세설명

    private ProjectDescriptionInfo() {
    }

    public ProjectDescriptionInfo(String projectSummary, String description) {
        this.projectSummary = projectSummary;
        this.description = description;
    }
}

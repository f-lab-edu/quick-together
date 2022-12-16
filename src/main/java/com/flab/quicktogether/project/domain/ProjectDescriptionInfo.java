package com.flab.quicktogether.project.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
public class ProjectDescriptionInfo {

    @Lob
    private String projectSummary; // 프로젝트 간단 설명

    @Lob
    private String description; // 프로젝트 상세설명

    protected ProjectDescriptionInfo() {
    }

    public ProjectDescriptionInfo(String projectSummary, String description) {
        this.projectSummary = projectSummary;
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDescriptionInfo that = (ProjectDescriptionInfo) o;
        return Objects.equals(getProjectSummary(), that.getProjectSummary()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectSummary(), getDescription());
    }
}

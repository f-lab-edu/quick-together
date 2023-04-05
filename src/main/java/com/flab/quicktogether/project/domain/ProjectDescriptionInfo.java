package com.flab.quicktogether.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class ProjectDescriptionInfo {


    @Column(columnDefinition = "LONGTEXT")
    private String projectSummary; // 프로젝트 간단 설명

    @Column(columnDefinition = "LONGTEXT")
    private String projectDescription; // 프로젝트 상세설명

    protected ProjectDescriptionInfo() {
    }

    public ProjectDescriptionInfo(String projectSummary, String projectDescription) {
        this.projectSummary = projectSummary;
        this.projectDescription = projectDescription;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDescriptionInfo that = (ProjectDescriptionInfo) o;
        return Objects.equals(getProjectSummary(), that.getProjectSummary()) && Objects.equals(getProjectDescription(), that.getProjectDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectSummary(), getProjectDescription());
    }
}

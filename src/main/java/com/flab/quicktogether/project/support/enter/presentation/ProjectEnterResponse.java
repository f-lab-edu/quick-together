package com.flab.quicktogether.project.support.enter.presentation;

import com.flab.quicktogether.project.support.enter.domain.Enter;
import com.flab.quicktogether.project.support.ProjectJoinStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectEnterResponse {

    @NotNull
    private Long projectId;

    @NotNull
    private Long enterMember;

    @NotNull
    private ProjectJoinStatus enterStatus;

    public ProjectEnterResponse(Enter enter) {
        this.projectId = enter.getProject().getId();
        this.enterMember = enter.getEnterMember().getId();
        this.enterStatus = enter.getEnterStatus();
    }
}
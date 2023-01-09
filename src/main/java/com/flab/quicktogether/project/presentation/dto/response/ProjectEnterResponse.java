package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.project.domain.Enter;
import com.flab.quicktogether.project.domain.EnterStatus;
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
    private EnterStatus enterStatus;

    public ProjectEnterResponse(Enter enter) {
        this.projectId = enter.getProject().getId();
        this.enterMember = enter.getEnterMember().getId();
        this.enterStatus = enter.getEnterStatus();
    }
}
package com.flab.quicktogether.project.presentation.dto.request;


import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.domain.MeetingMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProjectRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private String projectName; // 프로젝트 이름

    @NotNull @DateTimeFormat
    private LocalDateTime startDateTime; // 시작일

    @NotNull @DateTimeFormat
    private LocalDateTime periodDateTime; // 모집기간

    @NotNull
    private List<SkillStack> skillStacks = new ArrayList<>();

    @NotNull
    private List<Position> RecruitmentPositions = new ArrayList<>();

    @NotNull
    private MeetingMethod meetingMethod; // 미팅 방법

    @NotNull
    private String projectSummary; // 프로젝트 간단 설명

    @NotNull
    private String projectDescription; // 프로젝트 상세설명


    public CreateProjectRequestDto toServiceDto() {
        CreateProjectRequestDto dto = CreateProjectRequestDto.builder()
                .projectName(this.getProjectName())
                .memberId(this.getMemberId())
                .startDateTime(this.getStartDateTime())
                .periodDateTime(this.getPeriodDateTime())
                .meetingMethod(this.getMeetingMethod())
                .projectSummary(this.getProjectSummary())
                .projectDescription(this.getProjectDescription())
                .skillStacks(this.getSkillStacks())
                .recruitmentPositions(this.getRecruitmentPositions())
                .build();
        return dto;
    }
}

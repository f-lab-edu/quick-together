package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.common.CUDTimeStampInfo;
import com.flab.quicktogether.common.TimeSection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String projectName;

    @OneToOne
    @JoinColumn(name = "founder_id")
    private Founder founder;

    @Embedded
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    private MeetingMethod meetingMethod; // 진행방식

    private LocalDateTime startDateTime; // 시작일
    private LocalDateTime createDateTime; // 생성일

    private Long periodDate; // 모집기간

    private ProjectStatus projectStatus; // 프로젝트 상태

    private Integer likes; // 좋아요 수
    private Integer views; // 조회 수

    // 구성원 정보 표현


}



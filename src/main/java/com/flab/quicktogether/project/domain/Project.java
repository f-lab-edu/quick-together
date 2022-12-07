package com.flab.quicktogether.project.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Entity
@Builder
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String projectName;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "founder_id")
    private Founder founder;

    @Embedded
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.OPEN; // 프로젝트 상태

    @Enumerated(EnumType.STRING)
    private MeetingMethod meetingMethod; // 진행방식

    @Builder.Default
    private Integer likes = 0; // 좋아요 수

    @Builder.Default
    private Integer views = 0; // 조회 수

    private LocalDateTime startDateTime; // 시작일
    private LocalDateTime createDateTime; // 생성일

    private Long periodDate; // 모집기간

    // 구성원 정보 표현

}



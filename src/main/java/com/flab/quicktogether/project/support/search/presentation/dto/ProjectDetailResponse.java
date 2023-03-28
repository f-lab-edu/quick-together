package com.flab.quicktogether.project.support.search.presentation.dto;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.support.post.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProjectDetailResponse {

    private Long projectId;
    private String projectName; // 프로젝트 이름

    private String founderName;
    private Long founderId;

    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    private ProjectStatus projectStatus; // 프로젝트 상태

    private MeetingMethod meetingMethod; // 진행방식

    private Long likes; // 좋아요 수

    private Integer views; // 조회 수

    private LocalDateTime startDateTime; // 시작일

    private LocalDateTime periodDateTime; // 모집기간

    private LocalDateTime createDateTime; // 생성일

    private Set<SkillStack> projectSkillStacks = new HashSet<>();

    private Set<Position> recruitPositions = new HashSet<>();
    private List<ParticipantInfoDto> participants = new ArrayList<>();
    private List<PostInfoDto> posts = new ArrayList<>();



    public ProjectDetailResponse(Project p, Long likes, List<Post> allPosts) {
        projectId = p.getId();
        projectName = p.getProjectName();
        founderId = p.getFounder().getId();
        founderName = p.getFounder().getMemberName();
        startDateTime = p.getStartDateTime();
        periodDateTime = p.getPeriodDateTime();
        createDateTime = p.getCreateDateTime();
        meetingMethod = p.getMeetingMethod();
        projectDescriptionInfo = p.getProjectDescriptionInfo();
        projectStatus = p.getProjectStatus();
        views = p.getViews();
        projectSkillStacks = p.getSkillStacks();
        recruitPositions = p.getRecruitmentPositions();
        this.likes = likes;

        for (Post post : allPosts) {
            posts.add(new PostInfoDto(post.getMember().getMemberName(), post.getContent(), post.getCreateDateTime()));
        }

        for (Participant participant : p.getParticipants().participantsInfo()) {
            participants.add(new ParticipantInfoDto(participant.getMember().getMemberName(), participant.getSkillStacks(), participant.getPositions()));
        }

    }


}

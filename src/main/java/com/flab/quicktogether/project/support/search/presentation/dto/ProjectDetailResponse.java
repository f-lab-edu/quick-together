package com.flab.quicktogether.project.support.search.presentation.dto;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.support.post.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDetailResponse {

    private String projectName; // 프로젝트 이름

    private Long founder;

    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    private ProjectStatus projectStatus; // 프로젝트 상태

    private MeetingMethod meetingMethod; // 진행방식

    private Long likes; // 좋아요 수

    private Integer views; // 조회 수

    private LocalDateTime startDateTime; // 시작일

    private LocalDateTime periodDateTime; // 모집기간

    private List<SkillStack> projectSkillStacks = new ArrayList<>();

    private List<Position> recruitPositions = new ArrayList<>();
    private List<ParticipantInfoDto> participants = new ArrayList<>();
    private List<PostInfoDto> posts = new ArrayList<>();



    public ProjectDetailResponse(Project p, Long likes, List<Post> allPosts) {
        projectName = p.getProjectName();
        founder = p.getFounder().getId();
        startDateTime = p.getStartDateTime();
        periodDateTime = p.getPeriodDateTime();
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

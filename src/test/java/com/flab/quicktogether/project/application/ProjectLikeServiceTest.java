package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.DuplicateProjectLikeException;
import com.flab.quicktogether.project.exception.ProjectNoLikeException;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ProjectLikeServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectLikeService projectLikeService;


    private Member member;
    private Member member2;
    private Project project;

    private String projectName = "첫번째 프로젝트";
    private LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime periodDateTime = LocalDateTime.now();
    private MeetingMethod meetingMethod = MeetingMethod.SLACK;
    private String projectSummary = "간단할 설명~";
    private String projectDescription = "긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    @BeforeEach
    void initEach() {
        member = new Member("승재");
        member2 = new Member("승재2");
        memberRepository.save(member);
        memberRepository.save(member2);

        Project p = Project.builder()
                .projectName(projectName)
                .founder(member)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();
        project = p;
        projectRepository.save(project);
    }



    @Test
    @DisplayName("좋아요를 누를 경우 프로젝트에 좋아요가 1 증가한다.")
    void addProjectLike() {
        Long originalLike = 0L;
        projectLikeService.addProjectLike(project.getId(), member.getId());
        Assertions.assertEquals(originalLike+1,projectLikeService.totalLikes(project.getId()));
    }

    @Test
    @DisplayName("2명의 멤버가 한 프로젝트에 좋아요를 눌렀으시 좋아요 개수는 2 증가한다.")
    void addProjectLikes() {
        Long originalLike = 0L;
        projectLikeService.addProjectLike(project.getId(), member.getId());
        projectLikeService.addProjectLike(project.getId(), member2.getId());
        Assertions.assertEquals(originalLike+2,projectLikeService.totalLikes(project.getId()));
    }

    @Test
    @DisplayName("같은 멤버가 같은 프로젝트에 좋아요를 두번 할 시 중복 DuplicateProjectLikeException 발생한다.")
    void addProjectLikeException() {
        Assertions.assertThrows(DuplicateProjectLikeException.class, () -> {
            projectLikeService.addProjectLike(project.getId(), member.getId());
            projectLikeService.addProjectLike(project.getId(), member.getId());
        });
    }

    @Test
    @DisplayName("좋아요를 취소할 경우 좋아요 1 감소한다.")
    void cancelProjectLike() {
        Long originalLike = 1L;
        projectLikeService.addProjectLike(project.getId(), member.getId());
        projectLikeService.cancelProjectLike(project.getId(), member.getId());
        Assertions.assertEquals(originalLike-1,projectLikeService.totalLikes(project.getId()));
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 프로젝트에 좋아요 취소를 할 경우 ProjectNoLikeException 발생한다.")
    void cancelProjectLikeException() {
        Assertions.assertThrows(ProjectNoLikeException.class, () -> {
            projectLikeService.cancelProjectLike(project.getId(), member.getId());
        });
    }


}
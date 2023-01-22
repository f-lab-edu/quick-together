package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.support.post.application.ProjectPostService;
import com.flab.quicktogether.project.support.post.domain.Post;
import com.flab.quicktogether.project.support.post.infrastructure.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectPostServiceTest {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ProjectPostService projectPostService;
    @Autowired
    private PostRepository postRepository;

    private Project project;

    private Member member1;
    private Member member2;

    @BeforeEach
    void initEach() {
        member1 = new Member("홍재");
        member2 = new Member("승재");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Project p = Project.builder()
                .projectName("첫번째 프로젝트")
                .founder(member1)
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();
        project = p;
        projectRepository.save(project);
        Participant participant = new Participant(member1, project, ParticipantRole.ROLE_ADMIN);
        participantRepository.save(participant);
    }

    @Test
    @DisplayName("프로젝트에 게시글을 저장 후 게시글을 검증한다.")
    void createPost() {

        //given
        String content = "안녕하세요";

        //when
        Long postId = projectPostService.createPost(project.getId(), member1.getId(), content);
        Post post = postRepository.findById(postId).get();

        //then
        Assertions.assertEquals(post.getContent(), content);

    }

    @Test
    @DisplayName("프로젝트에 구성원이 아닌 멤버가 게시글을 저장 할 경우 Exception이 발생한다.")
    void createPostException() {

        //given
        String content = "안녕하세요";

        //when
        //then
        Assertions.assertThrows(ParticipantNotFoundException.class,() -> projectPostService.createPost(project.getId(), member2.getId(), content));

    }

    @Test
    @DisplayName("프로젝트에 게시글을 저장 후 내용을 수정한다.")
    void updatePost() {

        //given
        String content = "안녕하세요";
        String changeContent = "안녕하세요!!";
        projectPostService.createPost(project.getId(), member1.getId(), content);
        Long postId = projectPostService.createPost(project.getId(), member1.getId(), content);

        //when
        projectPostService.updatePost(project.getId(),member1.getId(),changeContent,postId);
        Post post = postRepository.findById(postId).get();

        //then
        Assertions.assertEquals(post.getContent(), changeContent);

    }
}
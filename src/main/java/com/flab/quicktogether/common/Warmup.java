//package com.flab.quicktogether.common;
//
//import com.flab.quicktogether.meeting.application.MeetingService;
//import com.flab.quicktogether.member.domain.Member;
//import com.flab.quicktogether.member.infrastructure.MemberRepository;
//import com.flab.quicktogether.project.domain.MeetingMethod;
//import com.flab.quicktogether.project.domain.Project;
//import com.flab.quicktogether.project.infrastructure.ProjectRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
////@Profile("dev")
//public class Warmup {
//
//    private final MemberRepository memberRepository;
//    private final ProjectRepository projectRepository;
//    private final MeetingService meetingService;
//
//    @Autowired
//    public Warmup(MemberRepository memberRepository, ProjectRepository projectRepository, MeetingService meetingService) {
//        this.memberRepository = memberRepository;
//        this.projectRepository = projectRepository;
//        this.meetingService = meetingService;
//    }
//
//    @PostConstruct
//    public void init(){
//        //멤버 가입
//        registMembers();
//        //프로젝트 생성
//        registProject();
//        //미팅 생성
//        registMeeting();
//
//
//    }
//
//    private void registMembers() {
//        //멤버 가입
//        Member testMember1 = Member.builder()
//                .name("test1")
//                .password("1111")
//                .email("test1@test.com")
//                .build();
//
//        Member testMember2 = Member.builder()
//                .name("test2")
//                .password("1111")
//                .email("test2@test.com")
//                .build();
//
//        Member testMember3 = Member.builder()
//                .name("test3")
//                .password("1111")
//                .email("test3@test.com")
//                .build();
//
//        memberRepository.saveAll(List.of(testMember1, testMember2,testMember3));
//    }
//
//    private void registProject() {
//        Member founder = memberRepository.findByMemberName("testMember1").get();
//
//        Project testProject = Project.builder()
//                .projectName("첫번째 프로젝트")
//                .founder(founder)
//                .startDateTime(LocalDateTime.now())
//                .periodDateTime(LocalDateTime.now())
//                .meetingMethod(MeetingMethod.SLACK)
//                .projectSummary("설명~")
//                .projectDescription("긴설명")
//                .build();
//        projectRepository.save(testProject);
//
//    }
//
//    private void registMeeting() {
//
//    }
//}

package com.flab.quicktogether.meeting.application;

import com.flab.quicktogether.meeting.domain.Meeting;
import com.flab.quicktogether.meeting.domain.MeetingRepository;
import com.flab.quicktogether.meeting.domain.exception.MeetingNotFoundException;
import com.flab.quicktogether.meeting.presentation.dto.MeetingRequestDto;
import com.flab.quicktogether.meeting.presentation.dto.MeetingInfo;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.support.post.application.ProjectPostService;
import com.flab.quicktogether.timeplan.application.ScheduleService;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.plan.PlanJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingService {


    private final ProjectRepository projectRepository;

    private final MeetingRepository meetingRepository;
    private final ScheduleService scheduleService;
    private final PlanJpaRepository planJpaRepository;

    private final ProjectPostService projectPostService;

    @Transactional
    public void regist(Long memberId, Long projectId, MeetingRequestDto meetingRequestDto) {

        Project project = findProject(projectId);

        MeetingInfo meetingInfo = meetingRequestDto.toMeetingInfo();

        // MeetingBuilder라는 도메인계층의 객체가 scheduleService를 의존하는것은 DIP위반으로 우려됨.
        // 그러나 미팅 생성시점에 미팅시간이 유효한지 아닌지를 검증하는것이 미팅생성후 별도의 검증로직을 작성해주는것보다 안전하다고 판단하여 아래와 같이 작성
        // 의문점은 scheduleService가 변경될경우 interface로 하고 의존성을 주입받으면 교체등의 보수관점에서는 문제가 없을 것 같은데 어떤 지점에서 문제가 생길지 떠오르지 않음.
        // 패키지순환참조가 문제된다고 하는데 어떤 문제가 발생할지는 정확하지 않음. 도메인계층이 서비스계층을 참조하고 서비스계층이 도메인서비스계층을 참조하는 형태인데 이것이 어떤문제를 일으킬지..
        Meeting meeting = Meeting.builder(memberId, project)
                .setMeetingInfo(meetingInfo)
                .validateAndBuild(scheduleService);

        //미팅 저장
        meetingRepository.save(meeting);

        //플랜 저장
        List<Plan> plans = meeting.createPlans();
        planJpaRepository.saveAll(plans);

        //Post 등록
        //Post post = meeting.createPost();

        //알람 발행
        //AlarmApi 아직 없음.


    }

    @Transactional
    public void requestRegistration(Long memberId, Long projectId, MeetingRequestDto meetingRequestDto) {
        Project project = findProject(projectId);

        MeetingInfo meetingInfo = meetingRequestDto.toMeetingInfo();

        Meeting meeting = Meeting.requestBuilder(memberId, project)
                .setMeetingInfo(meetingInfo)
                .validateAndBuild(scheduleService);

        //미팅 저장
        meetingRepository.save(meeting);
    }

    @Transactional
    public void approve(Long memberId, Long meetingId) {
        Meeting meeting = findMeeting(meetingId);

        meeting.approve(memberId);

        List<Plan> plans = meeting.createPlans();
        planJpaRepository.saveAll(plans);

        //Post 등록
        //Post post = meeting.createPost();

        //알람 발행
        //AlarmApi 아직 없음.


    }

    public void modify(Long loginMemberId, Long meetingId, MeetingRequestDto meetingRequestDto) {
        Meeting meeting = findMeeting(meetingId);

        MeetingInfo meetingInfo = meetingRequestDto.toMeetingInfo();

        meeting.update(meetingId, meetingInfo, scheduleService);
    }

    private Meeting findMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(MeetingNotFoundException::new);
        return meeting;
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }

    public void requestModification() {
    }

    public void delete() {

    }

    public void requestDelete() {

    }
}

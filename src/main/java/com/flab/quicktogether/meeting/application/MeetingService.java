package com.flab.quicktogether.meeting.application;

import com.flab.quicktogether.meeting.domain.Meeting;
import com.flab.quicktogether.meeting.domain.MeetingRepository;
import com.flab.quicktogether.meeting.domain.MeetingStatus;
import com.flab.quicktogether.meeting.domain.exception.MeetingNotFoundException;
import com.flab.quicktogether.meeting.presentation.dto.MeetingResponseDto;
import com.flab.quicktogether.meeting.presentation.dto.MeetingRequestDto;
import com.flab.quicktogether.meeting.domain.MeetingInfo;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.support.post.domain.Post;
import com.flab.quicktogether.project.support.post.infrastructure.PostRepository;
import com.flab.quicktogether.timeplan.application.ScheduleService;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.plan.PlanJpaRepository;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class MeetingService {


    private final ProjectRepository projectRepository;
    private final MeetingRepository meetingRepository;
    private final ScheduleService scheduleService;
    private final PlanJpaRepository planJpaRepository;
    private final PostRepository postRepository;

    @Autowired
    public MeetingService(ProjectRepository projectRepository,
                          MeetingRepository meetingRepository,
                          ScheduleService scheduleService,
                          PlanJpaRepository planJpaRepository,
                          PostRepository postRepository) {
        this.projectRepository = projectRepository;
        this.meetingRepository = meetingRepository;
        this.scheduleService = scheduleService;
        this.planJpaRepository = planJpaRepository;
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<MeetingResponseDto> getMeetingForApproval(Long loginMemberId, Long projectId, String timeZone) {
        Project project = findProject(projectId);
        project.getParticipants()
                .checkAdminAuth(loginMemberId);

        List<Meeting> meetingWaitingForAccepting = meetingRepository
                                                    .findMeetingsByProjectAndMeetingStatus(projectId, MeetingStatus.APPROVED);
        verifyExisting(meetingWaitingForAccepting);

        return meetingWaitingForAccepting.stream()
                .map(meeting -> MeetingResponseDto.from(meeting, timeZone))
                .toList();
    }

    public Long regist(Long memberId, Long projectId, MeetingRequestDto meetingRequestDto) {

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

        //ProjectPost.json 등록
        Post post = meeting.createPost();
        postRepository.save(post);

        //알람 발행
        //AlarmApi 아직 없음.
        return meeting.getId();
    }

    public void requestRegistration(Long memberId, Long projectId, MeetingRequestDto meetingRequestDto) {
        Project project = findProject(projectId);

        MeetingInfo meetingInfo = meetingRequestDto.toMeetingInfo();

        Meeting meeting = Meeting.requestBuilder(memberId, project)
                .setMeetingInfo(meetingInfo)
                .validateAndBuild(scheduleService);

        //미팅 저장
        meetingRepository.save(meeting);

        //ProjectPost.json 등록
        Post post = meeting.createPost();
        postRepository.save(post);

    }

    public void accept(Long memberId, Long meetingId) {
        Meeting meeting = findMeeting(meetingId);

        meeting.acceptCreation(memberId);

        List<Plan> plans = meeting.createPlans();
        planJpaRepository.saveAll(plans);

//        ProjectPost.json 등록
        Post post = meeting.createPost();
        postRepository.save(post);

        //알람 발행
        //AlarmApi 아직 없음.

    }

    public void deny(Long memberId, Long meetingId) {
        Meeting meeting = findMeeting(meetingId);
        meeting.denyCreation(memberId);

//        ProjectPost.json 등록
        Post post = meeting.createPost();
        postRepository.save(post);

        //알람 발행
        //AlarmApi 아직 없음.
    }

    public void modify(Long loginMemberId, Long meetingId, MeetingRequestDto meetingRequestDto) {
        Meeting meeting = findMeeting(meetingId);
        MeetingInfo meetingInfo = meetingRequestDto.toMeetingInfo();

        TimeBlock timeBlock = meeting.getTimeBlock();

        //meeting과 plan의 연관관계가 없어서 값비교를 통해 간접적으로 가져와서 수정
        List<Plan> plansMadeByThisMeeting = planJpaRepository
                .findByPlanNameAndEqualTimePeriod(meeting.getTitle(), timeBlock.getStartDateTime(), timeBlock.getEndDateTime());

        meeting.update(loginMemberId, meetingInfo, plansMadeByThisMeeting, scheduleService);


//        List<Plan> plans = meeting.createPlans();
//        planJpaRepository.saveAll(plans);

        Post post = meeting.createPost();
        postRepository.save(post);

    }

    public void requestModification(Long loginMemberId, Long meetingId, MeetingRequestDto meetingRequestDto) {
        Meeting meeting = findMeeting(meetingId);
        MeetingInfo meetingInfo = meetingRequestDto.toMeetingInfo();

        meeting.proposeModification(loginMemberId, meetingInfo);

        Post post = meeting.createPost();
        postRepository.save(post);


    }

    public void cancel(Long loginMemberId, Long meetingId) {
        //Meeting 캔슬
        Meeting meeting = findMeeting(meetingId);
        meeting.cancel(loginMemberId);

        //Plan 캔슬
        String title = meeting.getTitle();
        TimeBlock timeBlock = meeting.getTimeBlock();
        LocalDateTime from = timeBlock.getStartDateTime();
        LocalDateTime to = timeBlock.getEndDateTime();

        List<Plan> plansMadeByMeeting = planJpaRepository.findByPlanNameAndEqualTimePeriod(title, from, to);
        plansMadeByMeeting.forEach(plan-> plan.cancel());

        //Post발행
        Post post = meeting.createPost();
        postRepository.save(post);

    }

    public void requestCancelation(Long loginMemberId, Long meetingId) {
        Meeting meeting = findMeeting(meetingId);
        meeting.proposeDeletion(loginMemberId);

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

    private void verifyExisting(List<Meeting> meetingWaitingForAccepting) {
        if (meetingWaitingForAccepting.isEmpty()) {
            throw new MeetingNotFoundException();
        }
    }

    public MeetingResponseDto getMeeting(Long loginMemberId, Long meetingId, String timeZone) {
        Meeting meeting = findMeeting(meetingId);
        meeting.checkParticipant(loginMemberId);

        return MeetingResponseDto.from(meeting, timeZone);
    }
}

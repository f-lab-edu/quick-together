package com.flab.quicktogether.meeting.application;

import com.flab.quicktogether.globalsetting.domain.exception.ErrorCode;
import com.flab.quicktogether.meeting.presentation.AvailableTimeRequestDto;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.timeplan.application.Scheduler;
import com.flab.quicktogether.timeplan.application.TimePlanService;
import com.flab.quicktogether.timeplan.domain.etc.MinuteUnit;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.infrastructure.TimePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingSchedularService {


    private final ProjectRepository projectRepository;
    private final ParticipantRepository participantRepository;
    private final TimePlanService timePlanService;
    private final TimePlanRepository timePlanRepository;
    private final Scheduler scheduler;

    public List<TimeBlock> suggestAvailableTime(Long loginMemberId, Long projectId, AvailableTimeRequestDto availableTimeRequestDto) {
        MinuteUnit meetingTimeUnit = findProject(projectId).getMeetingTimeUnit();
        LocalDate roughlyStartDate = availableTimeRequestDto.getRoughlyStartDate();
        LocalDate roughlyEndDate = availableTimeRequestDto.getRoughlyEndDate();
        Integer meetingDuration = availableTimeRequestDto.getMeetingDuration();

        List<Participant> projectParticipants = participantRepository.findByProjectId(projectId);
        List<List<TimeBlock>> availableTimeLists = projectParticipants.stream()
                .map(projectParticipant -> projectParticipant.getMember().getId())
                .map(memberId -> timePlanRepository.findByMemberId(memberId).orElseThrow())
                .map(timePlan -> timePlan.extractAbleTimeBlock(roughlyStartDate, roughlyEndDate))
                .toList();

        return scheduler.suggestEventTime(meetingTimeUnit,roughlyStartDate,roughlyEndDate,meetingDuration,availableTimeLists);



    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND));
    }
}

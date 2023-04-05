package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.meeting.domain.Meeting;
import com.flab.quicktogether.meeting.domain.MeetingRepository;
import com.flab.quicktogether.meeting.domain.exception.MeetingNotFoundException;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.timeplan.domain.planner.RoughlyPlan;
import com.flab.quicktogether.timeplan.domain.planner.Planner;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlanRepository;
import com.flab.quicktogether.timeplan.domain.exception.NotFoundPlannerSettingException;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.plan.PlanJpaRepository;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSettingRepository;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.presentation.dto.RoughlyPlanDto;
import com.flab.quicktogether.timeplan.presentation.dto.SuggestedTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final MeetingRepository meetingRepository;
    private final PlanJpaRepository planJpaRepository;
    private final WeeklyAvailablePlanRepository weeklyAvailablePlanRepository;
    private final ParticipantRepository participantRepository;
    private final PlannerSettingRepository plannerSettingRepository;
    private final Planner planner;

    @Autowired
    public ScheduleService(MeetingRepository meetingRepository,
                           PlanJpaRepository planJpaRepository,
                           WeeklyAvailablePlanRepository weeklyAvailablePlanRepository,
                           ParticipantRepository participantRepository,
                           PlannerSettingRepository plannerSettingRepository,
                           Planner planner) {
        this.meetingRepository = meetingRepository;
        this.planJpaRepository = planJpaRepository;
        this.weeklyAvailablePlanRepository = weeklyAvailablePlanRepository;
        this.participantRepository = participantRepository;
        this.plannerSettingRepository = plannerSettingRepository;
        this.planner = planner;
    }

    public SuggestedTimeDto suggestAvailableTime(Long memberId, Long projectId, RoughlyPlanDto roughlyPlanDto) {

        Participant participant = participantRepository
                .findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(ParticipantNotFoundException::new);

        ZoneId zoneId = ZoneId.of(roughlyPlanDto.getTimeZone());
        RoughlyPlan roughlyPlan = roughlyPlanDto.toEntity();
        LocalDateTime limit = roughlyPlan.getRange().getStartDateTime();

        List<WeeklyAvailablePlan> weeklyAvailablePlansInProject = weeklyAvailablePlanRepository.findByProjectId(projectId);
        List<Plan> plansInProject = planJpaRepository.findByProjectId(projectId, limit);

        PlannerSetting plannerSetting = plannerSettingRepository.findScheduleSettingByMemberId(memberId)
                .orElseThrow(NotFoundPlannerSettingException::new);

        List<TimeBlock> suggestedBlock = planner.suggestTime(roughlyPlan, weeklyAvailablePlansInProject, plansInProject, plannerSetting);

        List<TimeBlock> suggestedBlockAsLocaltimeZone = suggestedBlock.stream()
                .map(block -> block.offsetLocaltimeZone(zoneId))
                .toList();
        return SuggestedTimeDto.from(suggestedBlockAsLocaltimeZone);
    }

    public void validToRegist(Project project, TimeBlock suggestionTime) {
        Long projectId = project.getId();

        LocalDateTime from = suggestionTime.getStartDateTime();
        LocalDateTime to = suggestionTime.getEndDateTime();

        List<WeeklyAvailablePlan> weeklyAvailablePlansInProject = weeklyAvailablePlanRepository.findByProjectId(projectId);
        List<Plan> plansInProject = planJpaRepository.findByProjectId(projectId, from, to);
        planner.verify(suggestionTime, weeklyAvailablePlansInProject, plansInProject);
    }

    public void validToModify(Long meetingId, Project project, TimeBlock suggestionTime) {
        Long projectId = project.getId();

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(MeetingNotFoundException::new);

        LocalDateTime from = suggestionTime.getStartDateTime();
        LocalDateTime to = suggestionTime.getEndDateTime();

        List<WeeklyAvailablePlan> weeklyAvailablePlansInProject = weeklyAvailablePlanRepository.findByProjectId(projectId);
        List<Plan> plansInProject = planJpaRepository.findByProjectId(projectId, from, to);

        //TODO Plan에서 Meeting영역을 빼고 비교해야하는데 SpringDataJpa를 사용하면서 어떻게 Detach를 사용하지?
        //우선 stream으로 새로운 리스트를 생성해주는 형태로 변경
        List<Plan> plansWithoutMeetingPlan = plansInProject.stream()
                .filter(plan -> !meeting.equals(plan))
                .toList();

        planner.verify(suggestionTime, weeklyAvailablePlansInProject, plansWithoutMeetingPlan);
    }

}

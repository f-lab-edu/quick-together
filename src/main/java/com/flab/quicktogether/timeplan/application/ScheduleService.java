package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.timeplan.domain.planner.RoughlyPlan;
import com.flab.quicktogether.timeplan.domain.planner.Planner;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlanRepository;
import com.flab.quicktogether.timeplan.domain.exception.NotFoundPlannerSettingException;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.plan.PlanJpaRepository;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.setting.ScheduleSettingRepository;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.presentation.dto.RoughlyPlanDto;
import com.flab.quicktogether.timeplan.presentation.dto.SuggestedTimeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final PlanJpaRepository planJpaRepository;
    private final WeeklyAvailablePlanRepository weeklyAvailablePlanRepository;
    private final ParticipantRepository participantRepository;
    private final ScheduleSettingRepository scheduleSettingRepository;
    private final Planner planner;


    public SuggestedTimeDto suggestAvailableTime(Long memberId, Long projectId, RoughlyPlanDto roughlyPlanDto) {

//        Participant participant = participantRepository
//                .findByProjectIdAndMemberId(projectId, memberId)
//                .orElseThrow(NotFoundParticipantException::new)

        ZoneId zoneId = ZoneId.of(roughlyPlanDto.getTimezone());
        RoughlyPlan roughlyPlan = roughlyPlanDto.toEntity();
        LocalDateTime limit = roughlyPlan.getRange().getStartDateTime();

        List<WeeklyAvailablePlan> weeklyAvailablePlansInProject = weeklyAvailablePlanRepository.findByProjectId(projectId);
        List<Plan> plansInProject = planJpaRepository.findByProjectId(projectId, limit);

        PlannerSetting plannerSetting = scheduleSettingRepository.findScheduleSettingByMemberId(memberId)
                .orElseThrow(NotFoundPlannerSettingException::new);

        List<TimeBlock> suggestedBlock = planner.suggestTime(roughlyPlan, weeklyAvailablePlansInProject, plansInProject, plannerSetting);

        List<TimeBlock> suggestedBlockAsLocalTimeZone = suggestedBlock.stream()
                .map(block -> block.offsetLocalTimeZone(zoneId))
                .toList();
        return SuggestedTimeDto.from(suggestedBlockAsLocalTimeZone);
    }

}

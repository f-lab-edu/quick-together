package com.flab.quicktogether.timeplan.domain.planner;

import com.flab.quicktogether.timeplan.domain.exception.NotAvailablePlanException;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.domain.exception.NotMatchableTimeException;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Planning을 위한 도메인서비스
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class Planner {
    public List<TimeBlock> suggestTime(RoughlyPlan roughlyPlan,
                                       List<WeeklyAvailablePlan> weeklyAvailablePlans,
                                       List<Plan> plans,
                                       PlannerSetting plannerSetting) {

        List<TimeBlock> candidateTimes = getCandidateTimes(roughlyPlan, plannerSetting);
        List<TimeBlock> commonSchedule = getCommonSchedule(roughlyPlan, weeklyAvailablePlans, plans);

        return suggest(candidateTimes, commonSchedule, plannerSetting);

    }

    public void verify(SuggestionTime suggestionTimeBlock, List<WeeklyAvailablePlan> weeklyAvailablePlans, List<Plan> plans) {
        Range range = Range.of(suggestionTimeBlock);

        List<TimeBlock> commonSchedule = getCommonSchedule(range, weeklyAvailablePlans, plans);

        if (!isAvailable(suggestionTimeBlock, commonSchedule)) {
            throw new NotAvailablePlanException();
        }
    }

    private List<TimeBlock> getCandidateTimes(RoughlyPlan roughlyPlan, PlannerSetting plannerSetting) {
        int unitValue = plannerSetting
                .getMinuteUnit()
                .getUnitValue();

        Long planDurationMinute = (long)roughlyPlan.getPlanDurationMinute();
        Integer marginalMinutes = plannerSetting.getMarginalMinutes();

        Range range = roughlyPlan.getRange();
        LocalDateTime targetStartDate = range.getStartDateTime()
                .plusMinutes(marginalMinutes);
        LocalDateTime targetEndTime = range.getEndDateTime();
        LocalDateTime endTarget = targetEndTime
                .minusMinutes(planDurationMinute)
                .minusMinutes(marginalMinutes);

        List<TimeBlock> candidateTimes = new ArrayList<>();

        LocalDateTime target = targetStartDate;
        while (target.compareTo(endTarget) <= 0) {
            candidateTimes.add(TimeBlock.of(target, target.plusMinutes(planDurationMinute)));
            target = target.plusMinutes(unitValue); // TODO LocalDateTime이 불변객체 추후 성능체크후 개선여지 있는지 확인
        }
        return candidateTimes;
    }

    private List<TimeBlock> getCommonSchedule(RoughlyPlan roughlyPlan, List<WeeklyAvailablePlan> weeklyAvailablePlans, List<Plan> plans) {
        Range range = roughlyPlan.getRange();
        return getCommonSchedule(range, weeklyAvailablePlans, plans);
    }
    private List<TimeBlock> getCommonSchedule(Range range, List<WeeklyAvailablePlan> weeklyAvailablePlans, List<Plan> plans) {
        List<TimeBlock> allOverlapAvailableSchedule = extractAllOverlapAvailableSchedule(weeklyAvailablePlans, range);
        return trim(allOverlapAvailableSchedule, plans);
    }

    private List<TimeBlock> suggest(List<TimeBlock> candidateTimes, List<TimeBlock> commonSchedule, PlannerSetting plannerSetting) {
        Integer marginalMinutes = plannerSetting.getMarginalMinutes();

        List<TimeBlock> allIncludedCandidateTimes = candidateTimes.stream()
                .filter(candidateTime -> candidateTime.isIncludeIn(commonSchedule, marginalMinutes))
                .collect(Collectors.toList());

        return applySetting(allIncludedCandidateTimes, plannerSetting);
    }

    private List<TimeBlock> applySetting(List<TimeBlock> allIncludedCandidateTimes, PlannerSetting plannerSetting) {
        Comparator<TimeBlock> comparator = plannerSetting.getSequencePriority().getComparator();
        Integer limit = plannerSetting.getLimit();

        Stream<TimeBlock> sortedTimeBlock = allIncludedCandidateTimes.stream()
                .sorted(comparator);

        if (limit == null) {
            return sortedTimeBlock.toList();
        }

        return sortedTimeBlock
                .limit(limit)
                .toList();
    }

    private List<TimeBlock> trim(List<TimeBlock> commonSchedule, List<Plan> plans) {
        if (plans == null || plans.isEmpty()) {
            return commonSchedule;
        }

        List<TimeBlock> disableTimeBlocks = plans.stream()
                .map(plan -> plan.getTimeBlock())
                .collect(Collectors.toList());

        return commonSchedule.stream()
                .flatMap(timeBlock -> timeBlock.trim(disableTimeBlocks).stream())
                .collect(Collectors.toList());
    }

    private List<TimeBlock> extractAllOverlapAvailableSchedule(List<WeeklyAvailablePlan> weeklyAvailablePlans, Range range) {
        List<TimeBlock> blocks = weeklyAvailablePlans.stream()
                .map(war -> war.extractAvailableTimeSchedule(range))
                .reduce(this::extractOverlap)
                .get();

        verifyEmpty(blocks);
        return blocks;
    }

    private void verifyEmpty(List<TimeBlock> blocks) {
        if (blocks.isEmpty()) {
            throw new NotMatchableTimeException();
        }
    }

    private List<TimeBlock> extractOverlap(List<TimeBlock> timeBlocks1, List<TimeBlock> timeBlocks2) {
        return timeBlocks1.stream()
                .flatMap(timeBlock1 -> timeBlocks2.stream()
                        .filter(timeBlock1::isOverLapped)
                        .map(timeBlock1::extractIntersection))
                .collect(Collectors.toList());
    }

    private boolean isAvailable(TimeBlock schedule, List<TimeBlock> commonSchedule) {
        boolean isAvailable = commonSchedule.stream()
                .anyMatch(schedule::isIncludeIn);
        return isAvailable;
    }
}

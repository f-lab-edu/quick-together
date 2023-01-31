package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.weekly_available_plan.AvailablePlan;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlanRepository;
import com.flab.quicktogether.timeplan.domain.exception.NoUniqueWeeklyAvailablePlanCreateException;
import com.flab.quicktogether.timeplan.domain.exception.NotFoundWeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanCreateRequestDto;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanGetDto;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeeklyAvailablePlanService {

    private final WeeklyAvailablePlanRepository weeklyAvailablePlanRepository;

    @Transactional
    public void registerWeeklyAvailableRoutine(Long memberId, AvailablePlanCreateRequestDto AvailablePlanCreateRequestDto) {
        weeklyAvailablePlanRepository.findByMemberId(memberId)
                .ifPresent(tp -> {throw new NoUniqueWeeklyAvailablePlanCreateException();});
        WeeklyAvailablePlan weeklyAvailablePlan = AvailablePlanCreateRequestDto.toEntityOf(memberId);
        weeklyAvailablePlanRepository.save(weeklyAvailablePlan);
    }

    @Transactional
    public void updateAvailablePlan(Long memberId, List<AvailablePlanRequestDto> availablePlanRequestDtos) {
        List<AvailablePlan> newAvailablePlan = availablePlanRequestDtos.stream()
                .flatMap(dto -> dto.toEntity().stream())
                .toList();

        weeklyAvailablePlanRepository.findByMemberId(memberId)
                .orElseThrow(NotFoundWeeklyAvailablePlan::new)
                .updateAbleRoutines(newAvailablePlan);
    }

    public AvailablePlanGetDto getAvailablePlan(Long loginMemberId, String localTimeZone) {
        ZoneId zoneId = ZoneId.of(localTimeZone);

        WeeklyAvailablePlan weeklyAvailablePlan = weeklyAvailablePlanRepository.findByMemberId(loginMemberId)
                .orElseThrow(NotFoundWeeklyAvailablePlan::new);
        return AvailablePlanGetDto.from(weeklyAvailablePlan, zoneId);
    }


}

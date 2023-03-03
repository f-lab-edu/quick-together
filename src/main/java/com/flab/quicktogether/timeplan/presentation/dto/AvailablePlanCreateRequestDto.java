package com.flab.quicktogether.timeplan.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
public class AvailablePlanCreateRequestDto {

    private String timezone;

    @JsonProperty("available_plans")
    private List<RegularTimeBlock> availablePlans = new ArrayList<>();

    protected AvailablePlanCreateRequestDto() {
    }

    public WeeklyAvailablePlan toEntityOf(Long memberId) {
        ZoneId localTimeZone = ZoneId.of(timezone);

        List<RegularTimeBlock> regularTimeBlocks = availablePlans.stream()
                .flatMap(dto -> {
                    DayOfWeek dayOfWeek = dto.getDayOfWeek();
                    LocalTime startTime = dto.getStartTime();
                    LocalTime endTime = dto.getEndTime();

                    return RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, localTimeZone)
                            .stream();
                })
                .collect(Collectors.toList());

        return new WeeklyAvailablePlan(memberId, regularTimeBlocks);
    }


}


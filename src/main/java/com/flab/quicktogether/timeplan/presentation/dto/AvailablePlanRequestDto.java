package com.flab.quicktogether.timeplan.presentation.dto;

import com.flab.quicktogether.timeplan.domain.weekly_available_plan.AvailablePlan;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AvailablePlanRequestDto {

    @NotNull
    private DayOfWeek dayOfWeek;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private String zoneId;

    public List<AvailablePlan> toEntity() {
        return RegularTimeBlock
                .asCommonTime(dayOfWeek, startTime, endTime, ZoneId.of(zoneId))
                .stream()
                .map(AvailablePlan::new)
                .collect(Collectors.toList());
    }

}

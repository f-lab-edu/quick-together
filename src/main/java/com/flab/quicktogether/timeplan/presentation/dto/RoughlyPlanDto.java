package com.flab.quicktogether.timeplan.presentation.dto;

import com.flab.quicktogether.timeplan.domain.planner.RoughlyPlan;
import com.flab.quicktogether.timeplan.domain.setting.MinuteUnit;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;


@Getter
public class RoughlyPlanDto {

    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate to;

    @NotNull
    private Integer planDurationMinute;

    @NotNull
    private String timezone;
    private MinuteUnit minuteUnit;
    public RoughlyPlan toEntity() {
        ZoneId zoneId = ZoneId.of(timezone);
        Range range = Range.asCommonTime(from, to, zoneId);
        return new RoughlyPlan(range, planDurationMinute, minuteUnit);
    }

}


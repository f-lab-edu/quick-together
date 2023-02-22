package com.flab.quicktogether.timeplan.domain.planner;

import com.flab.quicktogether.timeplan.domain.setting.MinuteUnit;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoughlyPlan {
    private final Range range;
    private final Integer planDurationMinute;

    private final MinuteUnit minuteUnit;
    public RoughlyPlan(Range range, Integer planDurationMinute, MinuteUnit minuteUnit) {
        this.range = range;
        this.planDurationMinute = planDurationMinute;
        this.minuteUnit = minuteUnit;
    }


    public LocalDateTime getLimit() {
        return this.range.getStartDateTime();
    }
}

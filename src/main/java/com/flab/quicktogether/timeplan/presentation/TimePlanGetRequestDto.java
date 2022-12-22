package com.flab.quicktogether.timeplan.presentation;

import com.flab.quicktogether.timeplan.domain.AbleRoutine;
import com.flab.quicktogether.timeplan.domain.Event;
import com.flab.quicktogether.timeplan.domain.TimePlan;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class TimePlanGetRequestDto {

    private List<AbleRoutine> ableRoutines = new ArrayList<>();
    private List<Event> plannedEvents = new ArrayList<>();

    public TimePlanGetRequestDto() {
    }

    public static TimePlanGetRequestDto from(TimePlan timePlan, List<Event> event) {
        TimePlanGetRequestDto timePlanGetRequestDto = new TimePlanGetRequestDto();
        timePlanGetRequestDto.ableRoutines.addAll(timePlan.getAbleRoutines());
        timePlanGetRequestDto.plannedEvents.addAll(event);
        return timePlanGetRequestDto;
    }

    @Override
    public String toString() {
        return "TimePlanCreateRequestDto{" +
                "ableRoutines=" + ableRoutines +
                ", plannedEvents=" + plannedEvents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimePlanGetRequestDto that)) return false;
        return Objects.equals(getAbleRoutines(), that.getAbleRoutines()) && Objects.equals(getPlannedEvents(), that.getPlannedEvents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbleRoutines(), getPlannedEvents());
    }
}

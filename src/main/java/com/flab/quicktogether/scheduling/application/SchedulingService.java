package com.flab.quicktogether.scheduling.application;

import com.flab.quicktogether.scheduling.domain.AvailableTimes;
import com.flab.quicktogether.scheduling.domain.Event;
import com.flab.quicktogether.common.TimeSection;

import java.time.LocalDate;
import java.util.List;

public class SchedulingService {

    public AvailableTimes getAvailableTimeWithEvent(Event event, AvailableTimes availableTimes) {
        LocalDate availableDate = availableTimes.getAvailableDate();

        TimeSection eventTimeSection = event.getTimeSection();

        List<TimeSection> availableTimesWithEventTimeSection = availableTimes.getAvailableTimes()
                .stream()
                .filter(a -> a.isAvailableWith(eventTimeSection)).toList();

        return new AvailableTimes(availableDate, availableTimesWithEventTimeSection);
    }


}

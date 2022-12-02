package com.flab.quicktogether.scheduling.domain;


import com.flab.quicktogether.common.TimeSection;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AvailableTimes {


    private Long id;

    private LocalDate availableDate;
    private List<TimeSection> availableTimes = new ArrayList<>();

    public AvailableTimes(LocalDate availableDate, List<TimeSection> availableTimes) {
        this.availableDate = availableDate;
        this.availableTimes = availableTimes;
    }

    public AvailableTimes addTime(LocalTime startTime, LocalTime endTime) {
        return this;
    }




}


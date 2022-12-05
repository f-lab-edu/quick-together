package com.flab.quicktogether.matching.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Embeddable
public class TimeSection {

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public TimeSection() {
    }

    public TimeSection(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSection that)) return false;
        return getStartTime().equals(that.getStartTime()) && getEndTime().equals(that.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return "TimeSection{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

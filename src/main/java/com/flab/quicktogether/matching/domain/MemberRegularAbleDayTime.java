package com.flab.quicktogether.matching.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@Embeddable
public class MemberRegularAbleDayTime {
    @Column(name="able_regular_day_of_week")
    DayOfWeek ableRegularDayOfWeek;

    @Embedded
    TimeSection timeSection;

    public MemberRegularAbleDayTime() {
    }

    public MemberRegularAbleDayTime(DayOfWeek ableRegularDayOfWeek, TimeSection timeSection) {
        this.ableRegularDayOfWeek = ableRegularDayOfWeek;
        this.timeSection = timeSection;
    }
}

package com.flab.quicktogether.common;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeSection {
    private LocalTime startTime;
    private LocalTime endTime;

    // 유효성 검사 startTime.isBefore(endTime)
    public TimeSection(LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(endTime)) throw new IllegalTimeDateException();
        this.startTime = startTime;
        this.endTime = endTime;
    }


    // 가능한 조건 |가능한시간 --- 이벤트시작시간 ---- 이벤트 종료시간 --- 가능한시간
    public boolean isAvailableWith(TimeSection eventTimeSection) {
        if (this.startTime.isAfter(eventTimeSection.startTime) && this.endTime.isBefore(eventTimeSection.endTime)) {
            return true;
        }
        return false;
    }


}


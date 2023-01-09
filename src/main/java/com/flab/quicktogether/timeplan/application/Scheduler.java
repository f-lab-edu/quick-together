package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.etc.MinuteUnit;

import java.time.LocalDate;
import java.util.List;

public interface Scheduler {
    List<TimeBlock> suggestEventTime(MinuteUnit minuteUnit,
                                     LocalDate targetStartDate,
                                     LocalDate targetEndDate,
                                     Integer EventPeriodByMinute,
                                     List<List<TimeBlock>> ableTimeBlocks);
}

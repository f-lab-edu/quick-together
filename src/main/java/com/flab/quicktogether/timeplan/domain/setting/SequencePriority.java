package com.flab.quicktogether.timeplan.domain.setting;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Getter;

import java.util.Comparator;

public enum SequencePriority {
    FAST(new FastTimeBlockComparator(),"fast"),
    LATE(new LateTimeBlockComparator(), "late");

    @Getter
    private final String value;
    @Getter
    private final Comparator<TimeBlock> comparator;

    SequencePriority(Comparator<TimeBlock> comparator, String value) {
        this.comparator = comparator;
        this.value =value;
    }




}

package com.flab.quicktogether.timeplan.domain.setting;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Getter;

import java.util.Comparator;

public enum SequencePriority {
    FAST(new FastTimeBlockComparator()),
    LATE(new LateTimeBlockComparator());

    @Getter
    private final Comparator<TimeBlock> comparator;

    SequencePriority(Comparator<TimeBlock> comparator) {
        this.comparator = comparator;
    }



}

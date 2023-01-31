package com.flab.quicktogether.timeplan.domain.setting;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;

import java.util.Comparator;

public class FastTimeBlockComparator implements Comparator<TimeBlock> {

    @Override
    public int compare(TimeBlock o1, TimeBlock o2) {
        return o1.compareTo(o2);
    }
}

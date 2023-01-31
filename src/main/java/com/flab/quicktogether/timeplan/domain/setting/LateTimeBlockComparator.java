package com.flab.quicktogether.timeplan.domain.setting;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;

import java.util.Comparator;

public class LateTimeBlockComparator implements Comparator<TimeBlock> {


    @Override
    public int compare(TimeBlock o1, TimeBlock o2) {
        return o2.compareTo(o1);
    }

}

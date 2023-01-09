package com.flab.quicktogether.timeplan.presentation;

import com.flab.quicktogether.timeplan.domain.AbleRoutine;
import com.flab.quicktogether.timeplan.domain.TimePlan;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.member.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
public class TimePlanCreateRequestDto {

    private List<RegularTimeBlock> ableRoutines = new ArrayList<>();

    protected TimePlanCreateRequestDto() {
    }

    public TimePlan toEntityOf(Member member) {
        List<AbleRoutine> routines = ableRoutines.stream()
                .map(AbleRoutine::new)
                .collect(Collectors.toList());

        return new TimePlan(member, routines, null);
    }

}


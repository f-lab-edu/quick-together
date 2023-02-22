package com.flab.quicktogether.timeplan.presentation.dto;

import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailablePlanGetDto {

    private List<RegularTimeBlock> availablePlans = new ArrayList<>();

    public AvailablePlanGetDto(List<RegularTimeBlock> availablePlans) {
        this.availablePlans.addAll(availablePlans);
    }

    public static AvailablePlanGetDto from(WeeklyAvailablePlan weeklyAvailablePlan, ZoneId localTimeZone) {
        List<RegularTimeBlock> localAvailablePlans =
                weeklyAvailablePlan.getAvailablePlans().stream()
                .flatMap(availablePlan -> availablePlan
                        .getRegularTimeBlock()
                        .toLocalTimeZone(localTimeZone).stream())
                .toList();

        List<RegularTimeBlock> gluedRegularBlocks = RegularTimeBlock.glue(localAvailablePlans);

        return new AvailablePlanGetDto(gluedRegularBlocks);
    }

}

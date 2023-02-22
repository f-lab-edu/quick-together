package com.flab.quicktogether.timeplan.presentation.dto;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuggestedTimeDto {
    List<TimeBlock> suggestedDateTimes = new ArrayList<>();

    private SuggestedTimeDto(List<TimeBlock> suggestedDateTimes) {
        suggestedDateTimes.addAll(suggestedDateTimes);
    }

    public static SuggestedTimeDto from(List<TimeBlock> suggestedDateTimes) {
        return new SuggestedTimeDto(suggestedDateTimes);
    }
}

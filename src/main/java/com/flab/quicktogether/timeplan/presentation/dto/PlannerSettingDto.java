package com.flab.quicktogether.timeplan.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.timeplan.domain.setting.MinuteUnit;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.setting.SequencePriority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PlannerSettingDto {
    @JsonProperty("sequence_priority")
    private SequencePriority sequencePriority;

    @JsonProperty("minute_unit")
    private MinuteUnit minuteUnit;

    @JsonProperty("marginal_minutes")
    private int marginalMinutes;

    @JsonProperty("limit")
    private Integer limit;

    @Builder
    private PlannerSettingDto(SequencePriority sequencePriority, MinuteUnit minuteUnit, int marginalMinutes, Integer limit) {
        this.sequencePriority = sequencePriority;
        this.minuteUnit = minuteUnit;
        this.marginalMinutes = marginalMinutes;
        this.limit = limit;
    }

    public PlannerSetting toEntity(Long memberId) {
        return PlannerSetting.builder()
                .memberId(memberId)
                .sequencePriority(sequencePriority)
                .minuteUnit(minuteUnit)
                .marginalMinutes(marginalMinutes)
                .limit(limit)
                .build();
    }

    public static PlannerSettingDto from(PlannerSetting plannerSetting) {
        return PlannerSettingDto.builder()
                .sequencePriority(plannerSetting.getSequencePriority())
                .minuteUnit(plannerSetting.getMinuteUnit())
                .marginalMinutes(plannerSetting.getMarginalMinutes())
                .limit(plannerSetting.getLimit())
                .build();
    }


}

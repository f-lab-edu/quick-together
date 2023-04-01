package com.flab.quicktogether.timeplan.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.timeplan.domain.setting.MinuteUnit;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.setting.SequencePriority;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PlannerSettingRequest {
    @JsonProperty("sequence_priority")
    private String sequencePriority;

    @JsonProperty("minute_unit")
    private int minuteUnit;

    @JsonProperty("marginal_minutes")
    private int marginalMinutes;

    @JsonProperty("limit")
    private Integer limit;

    public PlannerSetting toEntity(Long memberId) {
        return PlannerSetting.builder()
                .memberId(memberId)
                .sequencePriority(SequencePriority.valueOf(sequencePriority))
                .minuteUnit(MinuteUnit.valueOf(minuteUnit))
                .marginalMinutes(marginalMinutes)
                .limit(limit)
                .build();
    }


}

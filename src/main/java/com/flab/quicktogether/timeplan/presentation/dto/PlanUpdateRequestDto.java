package com.flab.quicktogether.timeplan.presentation.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class PlanUpdateRequestDto {

    @JsonProperty("event_name")
    private String eventName;

    @NotNull
    @JsonProperty("start_date_time")
    private LocalDateTime startDateTime;

    @NotNull
    @Future
    @JsonProperty("end_date_time")
    private LocalDateTime endDateTime;

    @NotNull
    @JsonProperty("time_zone")
    private String timeZone;

    public Plan toEntityOf(Long memberId) {
        return new Plan(memberId, this.eventName, TimeBlock.asCommonTime(startDateTime, endDateTime, ZoneId.of(timeZone)));
    }


}

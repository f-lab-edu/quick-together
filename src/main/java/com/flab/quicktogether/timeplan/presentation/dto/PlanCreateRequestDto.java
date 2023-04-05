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
@AllArgsConstructor
@ToString
public class PlanCreateRequestDto {

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

    public Plan toEntity(Long memberId) {
        TimeBlock timeBlock = TimeBlock.asCommonTime(startDateTime, endDateTime, ZoneId.of(timeZone));
        return new Plan(memberId, eventName, timeBlock);
    }
}

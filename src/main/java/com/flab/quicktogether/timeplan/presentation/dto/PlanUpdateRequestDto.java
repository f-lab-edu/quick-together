package com.flab.quicktogether.timeplan.presentation.dto;


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
    private String eventName;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    @Future
    private LocalDateTime endDateTime;

    @NotNull
    private String timeZone;

    public Plan toEntityOf(Long memberId) {
        return new Plan(memberId, this.eventName, TimeBlock.asCommonTime(startDateTime, endDateTime, ZoneId.of(timeZone)));
    }


}

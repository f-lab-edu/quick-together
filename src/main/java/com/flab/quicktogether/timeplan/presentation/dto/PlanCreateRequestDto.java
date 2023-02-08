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
@AllArgsConstructor
@ToString
public class PlanCreateRequestDto {
    private String eventName;

    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    @Future
    private LocalDateTime endDateTime;

    @NotNull
    private String timezone;

    public Plan toEntity(Long memberId) {
        TimeBlock timeBlock = TimeBlock.asCommonTime(startDateTime, endDateTime, ZoneId.of(timezone));
        return new Plan(memberId, eventName, timeBlock);
    }
}

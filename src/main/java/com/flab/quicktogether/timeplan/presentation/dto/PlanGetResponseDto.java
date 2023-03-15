package com.flab.quicktogether.timeplan.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanGetResponseDto {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("title")
    private final String planName;

    @JsonProperty("start")
    private final LocalDateTime startDateTime;

    @JsonProperty("end")
    private final LocalDateTime endDateTime;

    @JsonProperty("status")
    private final Plan.PlanStatus status;

    public static PlanGetResponseDto from(Plan plan, ZoneId zoneId){
        TimeBlock timeBlock = plan
                .getTimeBlock()
                .offsetLocaltimeZone(zoneId);

        LocalDateTime startDateTime = timeBlock.getStartDateTime();
        LocalDateTime endDateTime = timeBlock.getEndDateTime();

        return PlanGetResponseDto.builder()
                .id(plan.getId())
                .planName(plan.getPlanName())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .status(plan.getPlanStatus())
                .build();
    }
}

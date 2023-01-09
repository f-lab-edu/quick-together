package com.flab.quicktogether.timeplan.presentation;


import com.flab.quicktogether.timeplan.domain.Event;
import com.flab.quicktogether.timeplan.domain.TimePlan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventCreateRequestDto {
    private String eventName;

    @NotNull
    @URL
    private LocalDateTime startDateTime;
    @NotNull
    @Future
    private LocalDateTime endDateTime;

    public Event toEntity(TimePlan timePlan) {
        TimeBlock timeBlock = new TimeBlock(startDateTime, endDateTime);
        return new Event(timePlan, eventName, timeBlock);
    }
}

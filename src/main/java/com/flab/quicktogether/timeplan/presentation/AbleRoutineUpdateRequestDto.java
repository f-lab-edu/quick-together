package com.flab.quicktogether.timeplan.presentation;

import com.flab.quicktogether.timeplan.domain.AbleRoutine;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AbleRoutineUpdateRequestDto {

    @NotNull
    private DayOfWeek dayOfWeek;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    public AbleRoutine toEntity() {
        RegularTimeBlock regularTimeBlock = new RegularTimeBlock(dayOfWeek, startTime, endTime);
        return new AbleRoutine(regularTimeBlock);
    }

}

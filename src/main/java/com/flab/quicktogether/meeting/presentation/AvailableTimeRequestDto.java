package com.flab.quicktogether.meeting.presentation;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AvailableTimeRequestDto {
    @NotNull
    private LocalDate roughlyStartDate;

    @NotNull
    private LocalDate roughlyEndDate;

    @NotNull
    private Integer meetingDuration;

}

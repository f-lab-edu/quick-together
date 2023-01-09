package com.flab.quicktogether.timeplan.presentation;


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
public class EventUpdateRequestDto {
    private String eventName;

    @NotNull
    @URL
    private LocalDateTime startDateTime;
    @NotNull
    @Future
    private LocalDateTime endDateTime;


}

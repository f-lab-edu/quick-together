package com.flab.quicktogether.project.support.enter.presentation;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterMemberRequest {
    @NotNull
    private Long enterMemberId;
}

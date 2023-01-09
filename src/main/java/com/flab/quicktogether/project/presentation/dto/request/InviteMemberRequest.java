package com.flab.quicktogether.project.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InviteMemberRequest {
    @NotNull
    private Long requestMemberId;

    @NotNull
    private Long invitedMemberId;
}

package com.flab.quicktogether.member.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginFcmRequest {
    @NotEmpty
    private String memberName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String token;
}

package com.flab.quicktogether.common.auth.config.jwt;

import io.jsonwebtoken.Claims;
import lombok.Getter;

@Getter
public class TokenDto {

    private Claims token;
    private String memberName;
    private Long memberId;

    public TokenDto(Claims token, String memberName, Object memberId) {
        this.token = token;
        this.memberName = memberName;
        this.memberId = Long.valueOf(String.valueOf(memberId));
    }
}

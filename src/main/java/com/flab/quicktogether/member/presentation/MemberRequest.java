package com.flab.quicktogether.member.presentation;

import com.flab.quicktogether.member.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {
    @NotEmpty
    private String memberName;
    @NotEmpty
    private String password;

    public Member toEntity() {
        Member member = new Member(memberName, password);
        return member;
    }
}

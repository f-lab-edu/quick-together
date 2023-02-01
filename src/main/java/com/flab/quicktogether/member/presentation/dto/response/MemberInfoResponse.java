package com.flab.quicktogether.member.presentation.dto.response;

import com.flab.quicktogether.member.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoResponse {
    private String memberName; // 회원 아이디
    private String name; // 이름
    private String email;

    public MemberInfoResponse(String memberName, String name, String email) {
        this.memberName = memberName;
        this.name = name;
        this.email = email;
    }

    public static MemberInfoResponse toDto(Member member) {
        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(member.getMemberName(),member.getName(),member.getEmail());
        return memberInfoResponse;
    }
}

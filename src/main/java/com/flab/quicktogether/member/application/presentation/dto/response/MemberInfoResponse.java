package com.flab.quicktogether.member.application.presentation.dto.response;

import com.flab.quicktogether.member.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoResponse {

    private Long memberId;
    private String memberName; // 회원 아이디
    private String name; // 이름
    private String email;

    public MemberInfoResponse(Long memberId, String memberName, String name, String email) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.name = name;
        this.email = email;
    }

    public static MemberInfoResponse toDto(Member member) {
        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(member.getId(), member.getMemberName(),member.getName(),member.getEmail());
        return memberInfoResponse;
    }
}

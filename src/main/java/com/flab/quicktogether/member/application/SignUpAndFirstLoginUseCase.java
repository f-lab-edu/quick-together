package com.flab.quicktogether.member.application;

import com.flab.quicktogether.member.application.login.LoginService;
import com.flab.quicktogether.member.presentation.dto.request.MemberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpAndFirstLoginUseCase {

    private final MemberService memberService;
    private final LoginService loginService;

    @Autowired
    public SignUpAndFirstLoginUseCase(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }

    public Long execute(MemberRequest memberRequest) {
        String memberName = memberRequest.getMemberName();
        String password = memberRequest.getPassword();

        Long memberId = memberService.createMember(memberRequest);
        loginService.login(memberName, password);

        return memberId;
    }
}

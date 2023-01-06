package com.flab.quicktogether.member.presentation;

import com.flab.quicktogether.common.NotRequiredLoginCheck;
import com.flab.quicktogether.common.SessionConst;
import com.flab.quicktogether.member.application.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @NotRequiredLoginCheck
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity login(HttpSession httpSession) {
        Long loginId = memberService.login();
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, loginId);
        return ResponseEntity.ok().build();
    }
}

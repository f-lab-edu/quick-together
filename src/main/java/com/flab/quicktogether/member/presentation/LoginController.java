package com.flab.quicktogether.member.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.common.auth.NotRequiredLoginCheck;
import com.flab.quicktogether.member.application.login.LoginService;
import com.flab.quicktogether.member.presentation.dto.request.LoginRequest;
import com.flab.quicktogether.member.presentation.dto.response.MemberIdResponse;
import com.flab.quicktogether.member.presentation.dto.request.LoginRequest;
import com.flab.quicktogether.member.presentation.dto.response.MemberIdResponse;
import com.flab.quicktogether.member.presentation.dto.response.MemberSimpleResponse;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;

    @NotRequiredLoginCheck
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest) {

        Long loginId = loginService.login(loginRequest.getMemberName(),loginRequest.getPassword());

        return ResponseEntity.ok(new MemberIdResponse(loginId));
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity checkLogin(@Login Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return ResponseEntity.ok(new MemberSimpleResponse(memberId, findMember.getMemberName()));
    }


    @RequestMapping(path = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity logout(@Login Long memberId) {

        loginService.logout();

        return ResponseEntity.ok(new MemberIdResponse(memberId));
    }





}

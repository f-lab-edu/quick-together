package com.flab.quicktogether.member.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.common.auth.NotRequiredLoginCheck;
import com.flab.quicktogether.member.application.MemberLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberLoginService memberLoginService;

    @NotRequiredLoginCheck
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest) {

        Long loginId = memberLoginService.login(loginRequest.getMemberName(),loginRequest.getPassword());

        return ResponseEntity.ok(new MemberResponse(loginId));
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity isLogin(@Login Long memberId) {
        return ResponseEntity.ok(new MemberResponse(memberId));
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity logout(@Login Long memberId) {

        memberLoginService.logout();

        return ResponseEntity.ok(new MemberResponse(memberId));
    }





}

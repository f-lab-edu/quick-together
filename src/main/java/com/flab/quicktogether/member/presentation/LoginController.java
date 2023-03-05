package com.flab.quicktogether.member.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.common.auth.NotRequiredLoginCheck;
import com.flab.quicktogether.member.application.login.LoginService;
import com.flab.quicktogether.member.presentation.dto.request.LoginRequest;
import com.flab.quicktogether.member.presentation.dto.response.MemberIdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @NotRequiredLoginCheck
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest) {

        Long loginId = loginService.login(loginRequest.getMemberName(),loginRequest.getPassword());

        return ResponseEntity.ok(new MemberIdResponse(loginId));
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity checkLogin(@Login Long memberId) {
        return ResponseEntity.ok(new MemberIdResponse(memberId));
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity logout(@Login Long memberId) {

        loginService.logout();

        return ResponseEntity.ok(new MemberIdResponse(memberId));
    }

}

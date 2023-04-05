package com.flab.quicktogether.member.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.common.auth.NotRequiredLoginCheck;
import com.flab.quicktogether.member.application.MemberService;
import com.flab.quicktogether.member.application.SignUpAndFirstLoginUseCase;
import com.flab.quicktogether.member.presentation.dto.request.MemberRequest;
import com.flab.quicktogether.member.presentation.dto.response.MemberInfoResponse;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.presentation.dto.request.ChangePasswordRequest;
import com.flab.quicktogether.member.presentation.dto.response.MemberIdResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    private final SignUpAndFirstLoginUseCase signUpAndFirstLoginUseCase;

    /**
     * 회원 가입
     */
    @NotRequiredLoginCheck
    @RequestMapping(path = "/members", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody @Valid MemberRequest memberRequest) {

        Long memberId = signUpAndFirstLoginUseCase.execute(memberRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(memberId)
                .toUri();

        return ResponseEntity.created(location).body(new MemberIdResponse(memberId));
    }

    /**
     * 회원 탈퇴
     */
    @RequestMapping(path = "/members", method = RequestMethod.DELETE)
    public ResponseEntity deleteMember(@Login Long memberId) {

        Long deletedMemberId = memberService.deleteMember(memberId);

        return ResponseEntity.ok(new MemberIdResponse(deletedMemberId));
    }

    /**
     * 비밀번호 변경
     */
    @RequestMapping(path = "/me/password", method = RequestMethod.POST)
    public ResponseEntity changePassword(@Login Long memberId,
                                         @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {

        memberService.changePassword(memberId,changePasswordRequest.getPassword());

        return ResponseEntity.ok().build();
    }

    /**
     * 특정 멤버 조회
     * todo:관리자 권한 필요
     */
    @RequestMapping(path = "/members/{memberId}", method = RequestMethod.GET)
    public ResponseEntity retrieveMember(@PathVariable("memberId") Long memberId) {

        Member finedMember= memberService.retrieveMember(memberId);
        MemberInfoResponse memberInfoResponse = MemberInfoResponse.toDto(finedMember);

        return ResponseEntity.ok(memberInfoResponse);
    }

    /**
     * 전체 멤버 조회
     * todo:관리자 권한 필요
     */
    @RequestMapping(path = "/members", method = RequestMethod.GET)
    public Result retrieveAllMember() {
        List<Member> finedMembers = memberService.retrieveAllMembers();

        List<MemberInfoResponse> collect = finedMembers.stream()
                .map(member -> MemberInfoResponse.toDto(member))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    /**
     * 내 정보 조회
     */
    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public ResponseEntity myInfo(@Login Long memberId) {

        Member finedMember= memberService.retrieveMember(memberId);
        MemberInfoResponse memberInfoResponse = MemberInfoResponse.toDto(finedMember);

        return ResponseEntity.ok(memberInfoResponse);
    }
}

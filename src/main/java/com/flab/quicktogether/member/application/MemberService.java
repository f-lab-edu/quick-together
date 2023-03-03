package com.flab.quicktogether.member.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.DuplicateMemberNameException;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.member.presentation.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createMember(MemberRequest memberRequest){

        validateDuplicateMemberName(memberRequest);

        memberRequest.setPassword(passwordEncoder.encode(memberRequest.getPassword()));

        Member member = memberRequest.toEntity();
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();

    }

    private void validateDuplicateMemberName(MemberRequest memberRequest) {
        memberRepository.findByMemberName(memberRequest.getMemberName()).ifPresent(member -> {
                    throw new DuplicateMemberNameException();
                });
    }

    public boolean checkDuplicateMemberName(String memberName){
        boolean result = memberRepository.findByMemberName(memberName).isEmpty();
        return result;
    }

    @Transactional
    public void changePassword(Long memberId, String newPassword){
        Member finedMember = findMember(memberId);
        finedMember.changePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public Long deleteMember(Long memberId){

        Member finedMember = findMember(memberId);
        memberRepository.delete(finedMember);

        return finedMember.getId();
    }


    public Member retrieveMember(Long memberId){
        Member finedMember = findMember(memberId);
        return finedMember;

    }
    public List<Member> retrieveAllMembers() {
        return memberRepository.findAll();
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

}

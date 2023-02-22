package com.flab.quicktogether.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {
    @Id
    @GeneratedValue
    @Column(name = "FcmToken_id")
    private Long id;

    private Long memberId;

    private String token;

    public FcmToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}

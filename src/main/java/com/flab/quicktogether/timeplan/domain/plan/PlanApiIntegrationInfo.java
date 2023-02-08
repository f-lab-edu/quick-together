package com.flab.quicktogether.timeplan.domain.plan;

import jakarta.persistence.*;
import lombok.*;

/**
 * Plan의 API 연동정보를 담는 Entity
 * 지원여부에 따라 Plan의 CRUD를 각각에 실행한다.
 * 아직 연동구현이 안된 상태로 실질적인 인증프로세스를 구현하면서 변경될 예정
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class PlanApiIntegrationInfo {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    @Enumerated(EnumType.STRING)
    private Api api;

    @Lob
    private String authToken;

    private enum Api {
        SERVER,
        GOOGLE,
        NAVER,
        APPLE
    }
}

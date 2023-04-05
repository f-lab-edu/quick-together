package com.flab.quicktogether.timeplan.domain.setting;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlannerSetting {
    @Id
    @GeneratedValue
    @Column(name = "schedule_priority_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private SequencePriority sequencePriority;

    @Enumerated(EnumType.STRING)
    private MinuteUnit minuteUnit;

    private Integer marginalMinutes;

    @Column(name = "limits") // limit이 mariadb에서 예약어로 인식
    private int limit;

    @Builder
    public PlannerSetting(Long memberId, SequencePriority sequencePriority, MinuteUnit minuteUnit, Integer marginalMinutes, int limit) {
        this.memberId = memberId;
        this.sequencePriority = checkDefault(sequencePriority);
        this.minuteUnit = checkDefault(minuteUnit);
        this.marginalMinutes = checkDefault(marginalMinutes);
        this.limit = checkLimitDefault(limit);
    }

    public Long update(PlannerSetting plannerSetting) {
        this.sequencePriority = checkDefault(plannerSetting.sequencePriority);
        this.minuteUnit = checkDefault(plannerSetting.minuteUnit);
        this.marginalMinutes = checkDefault(plannerSetting.marginalMinutes);
        this.limit = checkLimitDefault(plannerSetting.limit);
        return this.id;
    }

    private int checkLimitDefault(Integer limit) {
        if (limit == null) {
            return 10;
        }
        return limit;
    }

    private Integer checkDefault(Integer marginalSpace) {
        if (marginalSpace == null) {
            return 0;
        }
        return marginalSpace;
    }

    private MinuteUnit checkDefault(MinuteUnit minuteUnit) {
        if (minuteUnit == null) {
            return MinuteUnit.TEN;
        }
        return minuteUnit;
    }

    private SequencePriority checkDefault(SequencePriority sequencePriority) {
        if (sequencePriority == null) {
            return sequencePriority.FAST;
        }
        return sequencePriority;
    }
}

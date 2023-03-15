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
    private SequencePriority sequencePriority = SequencePriority.FAST;

    @Enumerated(EnumType.STRING)
    private MinuteUnit minuteUnit = MinuteUnit.TEN;

    private Integer marginalMinutes = 0;

    private Integer limits;

    @Builder
    public PlannerSetting(Long memberId, SequencePriority sequencePriority, MinuteUnit minuteUnit, Integer marginalMinutes, Integer limits) {
        this.memberId = memberId;
        this.sequencePriority = checkDefault(sequencePriority);
        this.minuteUnit = checkDefault(minuteUnit);
        this.marginalMinutes = checkDefault(marginalMinutes);
        this.limits = limits;
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

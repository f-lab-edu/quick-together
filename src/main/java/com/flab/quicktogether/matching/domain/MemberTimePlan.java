package com.flab.quicktogether.matching.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
public class MemberTimePlan {

    @Id
    @GeneratedValue
    @Column(name = "member_time_plan_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_able_info")
    private MemberAbleInfo memberAbleInfo;

    //정기적으로 가능한 시간대는 UI상에 한번에 입력받도록 되어있고, 한번만 변경하면 되고 가능시간이 겹쳐지는것은 비즈니스로직상 불가한데 수정시 발생할 사이드이펙트를 고려하면 사용가능할듯하여 공부겸 적용해봄.
    //추후에 가능시간대 스냅샷을 히스토리로 남길생각이라면, Entity승격후 OneToMany단방향이 고려됨(위의 경우라 하더라도 딱히 이점이 없어서 이게 맞는것같긴함.)
    @ElementCollection
    @CollectionTable(name = "member_regular_able_day_time", joinColumns = @JoinColumn(name = "member_time_plan_id"))
    private List<MemberRegularAbleDayTime> memberRegularAbleDayTimes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="member_time_plan_id")
    private List<MemberPlannedEvent> memberPlannedEvents = new ArrayList<>();

    public MemberTimePlan() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberTimePlan that)) return false;
        return getId().equals(that.getId()) && getMemberAbleInfo().equals(that.getMemberAbleInfo()) && getMemberRegularAbleDayTimes().equals(that.getMemberRegularAbleDayTimes()) && getMemberPlannedEvents().equals(that.getMemberPlannedEvents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMemberAbleInfo(), getMemberRegularAbleDayTimes(), getMemberPlannedEvents());
    }
}



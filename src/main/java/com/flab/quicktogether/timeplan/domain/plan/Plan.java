package com.flab.quicktogether.timeplan.domain.plan;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredRangeException;
import com.flab.quicktogether.timeplan.domain.exception.IllegalPlanStateException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
@Entity
@ToString
@EqualsAndHashCode(exclude = "id")
public class Plan implements Comparable<Plan> {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    @Column(name = "plan_name")
    private String planName;

    @Embedded
    private TimeBlock timeBlock;

    @Enumerated(value = EnumType.STRING)
    private PlanStatus planStatus = PlanStatus.DEFAULT;;

    protected Plan() {
    }


    public Plan(Long memberId, String planName, TimeBlock timeBlock) {
        this.memberId = memberId;
        this.planName = nullProcess(planName);

        verifyNotExpire(timeBlock);
        this.timeBlock = timeBlock;
    }

    Plan(Long memberId, String planName, TimeBlock timeBlock, PlanStatus planStatus) {
        this.memberId = memberId;
        this.planName = nullProcess(planName);

        verifyNotExpire(timeBlock);
        this.timeBlock = timeBlock;
        this.planStatus = planStatus;
    }
    public static Plan madeByMeeting(Long memberId, String planName, TimeBlock timeBlock){
        return new Plan(memberId,planName,timeBlock,PlanStatus.MADE_BY_MEETING);
    }

    public static Plan asCommonTime(Long memberId, String planName, LocalDateTime startDateTime, LocalDateTime endDateTime, ZoneId localtimeZone) {
        TimeBlock commonTimeBlock = TimeBlock.asCommonTime(startDateTime, endDateTime, localtimeZone);
        return new Plan(memberId, planName, commonTimeBlock);
    }

    public void update(Plan plan) {
        verifyModifiable();
        if (notModified(plan)) return;

        this.planName = plan.getPlanName();
        this.timeBlock = plan.getTimeBlock();
    }

    private boolean notModified(Plan plan) {
        return this.equals(plan);
    }

    public void cancel() {
        verifyModifiable();
        this.planStatus = PlanStatus.DELETED;
    }

    private void verifyModifiable() {
        if (this.planStatus.equals(PlanStatus.DELETED)) {
            throw new IllegalPlanStateException();
        }
    }

    private static String nullProcess(String eventName) {
        if (eventName == null) {
            eventName = "";
        }
        return eventName;
    }

    private static void verifyNotExpire(TimeBlock timeBlock) {
        boolean isExpired = timeBlock.getEndDateTime().compareTo(LocalDateTime.now()) <= 0;
        if (isExpired) {
            throw new ExpiredRangeException();
        }
    }

    @Override
    public int compareTo(Plan o) {
        return this.timeBlock.compareTo(o.getTimeBlock());
    }

    public void update(String planName, TimeBlock timeBlock) {
        this.planName = planName;
        this.timeBlock = timeBlock;
    }

    public enum PlanStatus {
        DEFAULT, DELETED, MADE_BY_MEETING
    }
}

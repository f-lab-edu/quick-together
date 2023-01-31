package com.flab.quicktogether.timeplan.domain.plan;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.exception.IllegalEventStateException;
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

    public static Plan asCommonTime(Long memberId, String planName, LocalDateTime startDateTime, LocalDateTime endDateTime, ZoneId localTimeZone) {
        TimeBlock commonTimeBlock = TimeBlock.asCommonTime(startDateTime, endDateTime, localTimeZone);
        return new Plan(memberId, planName, commonTimeBlock);
    }

    public void updateEvent(Plan plan) {
        verifyModifiable();
        if (notModified(plan)) return;

        this.planName = plan.getPlanName();
        this.timeBlock = plan.getTimeBlock();
    }

    private boolean notModified(Plan plan) {
        return this.equals(plan);
    }

    public void delete() {
        verifyModifiable();
        this.planStatus = PlanStatus.DELETED;
    }

    private void verifyModifiable() {
        if (this.planStatus.equals(PlanStatus.DELETED)) {
            throw new IllegalEventStateException();
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
            throw new ExpiredTimeException();
        }
    }

    @Override
    public int compareTo(Plan o) {
        return this.timeBlock.compareTo(o.getTimeBlock());
    }

    private enum PlanStatus {
        DEFAULT, DELETED
    }
}

package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 정기적으로 가능한 시간
 */

@Getter
@Entity
@Table(name = "available_plan")
@ToString
public class AvailablePlan implements Comparable<AvailablePlan> {

    @Id @GeneratedValue
    @Column(name = "available_plan_id")
    private Long id;

    @Embedded
    private RegularTimeBlock regularTimeBlock;

    public AvailablePlan() {
    }

    public AvailablePlan(RegularTimeBlock regularTimeBlock) {
        this.regularTimeBlock = regularTimeBlock;
    }

    public List<TimeBlock> extractAvailableTime(Range range) {
        verifyExpiredDate(range);
        return regularTimeBlock.extractTimeBlocks(range);
    }

    private static void verifyExpiredDate(Range range) {
        LocalDateTime endDateTime = range.getEndDateTime();
        if (endDateTime.compareTo(LocalDateTime.now()) <= 0) {
            throw new ExpiredTimeException();
        }
    }

    @Override
    public int compareTo(AvailablePlan o) {
        return this.regularTimeBlock.compareTo(o.regularTimeBlock);
    }
}

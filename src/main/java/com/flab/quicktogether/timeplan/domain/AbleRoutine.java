package com.flab.quicktogether.timeplan.domain;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


/**
 * 정기적으로 가능한 시간
 */

@Getter
@Entity
@Table(name = "able_routine")
public class AbleRoutine implements Comparable<AbleRoutine> {

    @Id @GeneratedValue
    @Column(name = "able_routine_id")
    private Long id;

    @Embedded
    private RegularTimeBlock regularTimeBlock;

    public AbleRoutine() {
    }

    public AbleRoutine(RegularTimeBlock regularTimeBlock) {
        this.regularTimeBlock = regularTimeBlock;
    }

    public List<TimeBlock> generateAbleTimeBlock(LocalDate from, LocalDate to) {
        if (to.isBefore(LocalDate.now())) {
            throw new ExpiredTimeException();
        }
        return regularTimeBlock.generateAbsoluteTimeBlocks(from, to);
    }

    @Override
    public int compareTo(AbleRoutine o) {
        return this.regularTimeBlock.compareTo(o.regularTimeBlock);
    }
}

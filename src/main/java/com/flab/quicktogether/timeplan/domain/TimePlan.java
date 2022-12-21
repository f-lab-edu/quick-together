package com.flab.quicktogether.timeplan.domain;

import com.flab.quicktogether.timeplan.domain.exception.AbleRoutineOverlapException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

/**
 * 시간계획
 * 목적 : 시간계획을 통해 비교할수있는 데이터 생성
 * 시간계획은 정기적인 가능시간인 ableRoutine과 불가능한 시간인 PlannedEvent로 구성됨.
 */
@Entity
@Getter
@EqualsAndHashCode
public class TimePlan {

    @Id
    @GeneratedValue
    @Column(name = "time_plan_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "time_plan_id")
    private List<AbleRoutine> ableRoutines = new ArrayList<>();

    protected TimePlan() {
    }

    public TimePlan(Member member, List<AbleRoutine> ableRoutines) {
        this.member = member;
        verifySeperated(ableRoutines);
        this.ableRoutines = ableRoutines;
    }

    /**
     * ableRoutines검증
     *
     * @param ableRoutines
     * @return
     */
    private void verifySeperated(List<AbleRoutine> ableRoutines) {
        Collections.sort(ableRoutines);
        for (int i = 0; i < ableRoutines.size()-1; i++) {
            RegularTimeBlock cur = ableRoutines.get(i).getRegularTimeBlock();
            RegularTimeBlock next = ableRoutines.get(i + 1).getRegularTimeBlock();

            if (!cur.isSeperatedFrom(next)) {
                throw new AbleRoutineOverlapException();
            }
        }
    }

    public void updateAbleRoutines(List<AbleRoutine> newAbleRoutine) {
        List<AbleRoutine> NotExistingNewRoutines = newAbleRoutine.stream()
                .filter(ableRoutine -> !this.ableRoutines.contains(ableRoutine))
                .toList();

        for (AbleRoutine NotExistingNewRoutine : NotExistingNewRoutines) {
            updateAbleRoutine(NotExistingNewRoutine);
        }
    }

    public void updateAbleRoutine(AbleRoutine newAbleRoutine) {
        List<AbleRoutine> overlappedAbleRoutine = this.ableRoutines.stream()
                .filter(ar -> !ar.getRegularTimeBlock().isSeperatedFrom(newAbleRoutine.getRegularTimeBlock()))
                .toList();
        this.ableRoutines.removeAll(overlappedAbleRoutine);
        this.ableRoutines.add(newAbleRoutine);
    }



    /**
     * 비교를 위한 최종 DateTimeSection을 뽑아내는 작업
     */
    public List<TimeBlock> extractAbleTimeBlock(List<Event> plannedEvents, LocalDate targetStartDate, LocalDate targetEndDate) {
        //1. MemberAbleRoutine 절대화 -> List<DateTimeSection>
        List<TimeBlock> convertedAbleRoutines = convertAbsoluteBlock(targetStartDate, targetEndDate);

        //2. PlannedEvents trimming -> List<DateTimeSection> 정규가능시간에서 존재하는 일정 빼기
        List<TimeBlock> absoluteDateTimePlan = new ArrayList<>();
//        for (PlannedEvent event : plannedEvents) {
//            List<TimeBlock> trimmedTimeBlocks = convertedAbleRoutines.stream()
//                    .filter(timeBlock -> timeBlock.isOverLapped(event.getTimeBlock()))
//                    .map(timeBlock -> timeBlock.trim(event.getTimeBlock()))
//                    .flatMap(Collection::stream)
//                    .toList();
//            absoluteDateTimePlan.addAll(trimmedTimeBlocks);
//        }

        for (TimeBlock convertedAbleRoutine : convertedAbleRoutines) {

            List<TimeBlock> trimmedTimeBlock = new ArrayList<>();
            for (Event plannedEvent : plannedEvents) {
                if (convertedAbleRoutine.isOverLapped(plannedEvent.getTimeBlock())) {
                    List<TimeBlock> trim = convertedAbleRoutine.trim(plannedEvent.getTimeBlock());
                    trimmedTimeBlock.addAll(trim);
                }
            }

            TimeBlock allTrimmedTimeBlock = null;
            for (TimeBlock a : trimmedTimeBlock) {
                for (TimeBlock b : trimmedTimeBlock) {
                    if(a.isOverLapped(b))
                    a.extractIntersection(b);
                }
            }
        }



        return absoluteDateTimePlan;
    }


    public List<TimeBlock> convertAbsoluteBlock(LocalDate targetStartDate, LocalDate targetEndDate) {
        return ableRoutines.stream()
                .map(r -> r.generateAbleTimeBlock(targetStartDate, targetEndDate))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .toList();
    }


}



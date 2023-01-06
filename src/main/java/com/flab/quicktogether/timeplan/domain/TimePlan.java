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

    @OneToMany(mappedBy = "timePlan")
    private List<Event> plannedEvents = new ArrayList<>();
    protected TimePlan() {
    }

    public TimePlan(Member member, List<AbleRoutine> ableRoutines,List<Event> plannedEvents) {
        this.member = member;
        verifySeperated(ableRoutines);
        this.ableRoutines = ableRoutines;
        this.plannedEvents= plannedEvents;
    }

    /**
     * ableRoutines검증 ableRoutine간의 블록은 서로 겹칠수 없음.
     *
     * @param ableRoutines
     * @return
     */
    private void verifySeperated(List<AbleRoutine> ableRoutines) {
        Collections.sort(ableRoutines);

        Iterator<AbleRoutine> iterator = ableRoutines.iterator();
        RegularTimeBlock curRtb = iterator.next().getRegularTimeBlock();

        while (iterator.hasNext()) {
            RegularTimeBlock nextRtb = iterator.next().getRegularTimeBlock();

            if (!curRtb.isSeperatedFrom(nextRtb)) {
                throw new AbleRoutineOverlapException();
            }
            curRtb = nextRtb;
        }

        //Stream Reduce 사용 -> ExceptionInInitializerError 발생
//        ableRoutines.stream().reduce((x,y)-> {
//            RegularTimeBlock xRtb = x.getRegularTimeBlock();
//            RegularTimeBlock yRtb = y.getRegularTimeBlock();
//
//            if (xRtb.isSeperatedFrom(yRtb)) {
//                throw new AbleRoutineOverlapException();
//            }
//            return y;
//        });



//        for (int i = 0; i < ableRoutines.size()-1; i++) {
//            RegularTimeBlock cur = ableRoutines.get(i).getRegularTimeBlock();
//            RegularTimeBlock next = ableRoutines.get(i + 1).getRegularTimeBlock();
//
//            if (!cur.isSeperatedFrom(next)) {
//                throw new AbleRoutineOverlapException();
//            }
//        }
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
    public List<TimeBlock> extractAbleTimeBlock(LocalDate targetStartDate, LocalDate targetEndDate) {
        List<TimeBlock> convertedAbleRoutines = convertAbsoluteBlock(targetStartDate, targetEndDate);

        return trimPlannedEvents(convertedAbleRoutines, plannedEvents);
    }

    public List<TimeBlock> convertAbsoluteBlock(LocalDate targetStartDate, LocalDate targetEndDate) {
        return ableRoutines.stream()
                .map(r -> r.generateAbleTimeBlock(targetStartDate, targetEndDate))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .toList();
    }

    private List<TimeBlock> trimPlannedEvents(List<TimeBlock> convertedAbleRoutines, List<Event> plannedEvents) {
        List<TimeBlock> absoluteDateTimePlan = new ArrayList<>();

        for (TimeBlock convertedAbleRoutine : convertedAbleRoutines) {

            List<TimeBlock> eachTrimmedTimeBlock = new ArrayList<>();
            for (Event plannedEvent : plannedEvents) {
                if (convertedAbleRoutine.isOverLapped(plannedEvent.getTimeBlock())) {
                    List<TimeBlock> trim = convertedAbleRoutine.trim(plannedEvent.getTimeBlock());
                    eachTrimmedTimeBlock.addAll(trim);
                }
            }

            for (TimeBlock a : eachTrimmedTimeBlock) {
                TimeBlock allTrimmedTimeBlock = a;
                for (TimeBlock b : eachTrimmedTimeBlock) {
                    if(allTrimmedTimeBlock.isOverLapped(b))
                        allTrimmedTimeBlock = allTrimmedTimeBlock.extractIntersection(b);
                }
                absoluteDateTimePlan.add(allTrimmedTimeBlock);
            }
        }

        return absoluteDateTimePlan.stream().distinct().toList();
    }


}



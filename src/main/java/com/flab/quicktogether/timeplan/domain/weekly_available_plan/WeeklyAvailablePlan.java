package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import com.flab.quicktogether.timeplan.domain.exception.AvailablePlanOverlapException;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 주간 가능한 시간대를 모두 갖고있는 Entity
 * 기간을 지정하여 날짜를 반영한 TimeBlock을 생성할 수 있다.
 */
@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeeklyAvailablePlan {

    @Id
    @GeneratedValue
    @Column(name = "weekly_available_plan_id")
    private Long id;

    private Long memberId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weekly_available_plan_id")
    private List<AvailablePlan> availablePlans = new ArrayList<>();

    public WeeklyAvailablePlan(Long memberId, List<RegularTimeBlock> regularTimeBlocks) {
        this.memberId = memberId;
        verifySeperated(regularTimeBlocks);
        this.availablePlans = convertAvailablePlan(regularTimeBlocks);
    }

    /**
     * 합쳐야 할 값을 합치고 AvailablePlan Entity로 변환하여 반환
     * @param regularTimeBlocks
     * @return
     */
    private List<AvailablePlan> convertAvailablePlan(List<RegularTimeBlock> regularTimeBlocks) {
        return RegularTimeBlock.glue(regularTimeBlocks)
                .stream()
                .map(AvailablePlan::new)
                .toList();
    }

    /**
     * ableRoutines검증 ableRoutine간의 블록은 서로 겹칠수 없음.
     *
     * @param
     * @return
     */
    private void verifySeperated(List<RegularTimeBlock> regularTimeBlocks) {
        Collections.sort(regularTimeBlocks);
        Iterator<RegularTimeBlock> iterator = regularTimeBlocks.iterator();
        RegularTimeBlock curRtb = iterator.next();

        while (iterator.hasNext()) {
            RegularTimeBlock nextRtb = iterator.next();

            if (!curRtb.isSeperatedFrom(nextRtb)) {
                throw new AvailablePlanOverlapException();
            }
            curRtb = nextRtb;
        }
    }

    public void updateAvailablePlans(List<AvailablePlan> newAvailablePlan) {
        List<AvailablePlan> NotExistingNewRoutines = newAvailablePlan.stream()
                .filter(availablePlan -> !this.availablePlans.contains(availablePlan))
                .toList();

        for (AvailablePlan NotExistingNewRoutine : NotExistingNewRoutines) {
            updateAvailablePlans(NotExistingNewRoutine);
        }
    }

    private void updateAvailablePlans(AvailablePlan newAvailablePlan) {
        List<AvailablePlan> overlappedAvailablePlan = this.availablePlans.stream()
                .filter(ar -> !ar.getRegularTimeBlock().isSeperatedFrom(newAvailablePlan.getRegularTimeBlock()))
                .toList();
        this.availablePlans.removeAll(overlappedAvailablePlan);
        this.availablePlans.add(newAvailablePlan);
    }

    public List<TimeBlock> extractAvailableTimeSchedule(Range range) {
        List<TimeBlock> convertedSchedule = availablePlans.stream()
                        .flatMap(ap -> ap.extractAvailableTime(range).stream())
                        .filter(Objects::nonNull)
                        .sorted()
                        .collect(Collectors.toList());

        return TimeBlock.glue(convertedSchedule);
    }


}



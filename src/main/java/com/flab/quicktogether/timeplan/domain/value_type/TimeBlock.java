package com.flab.quicktogether.timeplan.domain.value_type;

import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 시작시간과 종료시간을 통해 구간을 구현한 값타입.
 * 종료시간이 시작시간을 앞설수 없음.
 * 구간으로 구간을 잘라내는 trim구현
 */
@Getter
@Embeddable
@EqualsAndHashCode
@ToString
public class TimeBlock {
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    protected TimeBlock() {
    }

    public TimeBlock(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        verifyNaturalTimeOrder(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void verifyNaturalTimeOrder(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime) || startDateTime.equals(endDateTime)) {
            throw new NotNaturalTimeOrderException();
        }
    }

    public List<TimeBlock> trim(TimeBlock target) {

        ArrayList<TimeBlock> trimmedBlocks = new ArrayList<>();

        if (this.equals(target) || this.isIncludeIn(target)) {
        } else if (this.isOverlappedWithStartEqualBy(target) || this.isOverlappedForwardBy(target)) {
            trimmedBlocks.add(this.remainBackward(target));

        } else if (this.isOverlappedWithEndEqualBy(target) || this.isOverlappedBackwardBy(target)) {
            trimmedBlocks.add(this.remainForward(target));

        } else if (this.isCover(target)) {
            trimmedBlocks.addAll(separate(target));

        } else if (this.isApart(target)) {
            trimmedBlocks.add(this);

        } else if (this.isContinue(target)) {
            trimmedBlocks.add(this);
        }
        return trimmedBlocks;

    }
//    TODO 간결하게 작성하면 왜 안되는지 확인 필요.
//        if (this.startDateTime.compareTo(target.startDateTime) >= 0
//                && this.endDateTime.compareTo(target.endDateTime) <= 0) {
//        } else if (this.startDateTime.compareTo(target.startDateTime) <= 0
//                && (this.endDateTime.compareTo(target.startDateTime) > 0 && this.endDateTime.compareTo(target.endDateTime) < 0)) {
//            TimeBlock timeBlock = new TimeBlock(this.startDateTime, target.startDateTime);
//            trimmedBlocks.add(timeBlock);
//
//        } else if ((this.startDateTime.compareTo(target.startDateTime) > 0 && this.startDateTime.compareTo(target.endDateTime) < 0)
//                && this.endDateTime.compareTo(target.endDateTime) <= 0) {
//            TimeBlock timeBlock = new TimeBlock(target.endDateTime, this.endDateTime);
//            trimmedBlocks.add(timeBlock);
//
//        } else if (this.startDateTime.compareTo(target.startDateTime) < 0 && this.endDateTime.compareTo(target.endDateTime) > 0) {
//            TimeBlock timeBlock = new TimeBlock(this.startDateTime, target.startDateTime);
//            TimeBlock addedTimeBlock = new TimeBlock(target.endDateTime, this.endDateTime);
//            trimmedBlocks.addAll(List.of(timeBlock, addedTimeBlock));
//        } else {
//            trimmedBlocks.add(this);
//        }
//        return trimmedBlocks;
//    }


    public List<TimeBlock> trim(List<TimeBlock> targets) {
        List<TimeBlock> eachTrimmedTimBlock = targets.stream()
                .filter(timeBlock -> timeBlock.isOverLapped(this))
                .map(timeBlock -> this.trim(timeBlock))
                .flatMap(Collection::stream)
                .toList();

        return eachTrimmedTimBlock;
    }



    /**
     * 포함여부는 Overlap과 의미적으로 겹치는 부분이 존재하여 아래사항에서는 포함여부에서 제외
     * - isIncludeIn에서 시작점이 같은 경우나, 끝점이 같은 경우는 포함되지 않음.
     * - 시작점이 같은경우는 isOverlappingForward에서 true
     * - 종료점이 같은 경우에는 isOverlappingBackward에서 true
     *
     * @param target
     * @return
     */
    private boolean isOverlappedWithStartEqualBy(TimeBlock target) {
        return this.startDateTime.isEqual(target.startDateTime)
                && this.endDateTime.isAfter(target.endDateTime);
    }

    private boolean isOverlappedWithEndEqualBy(TimeBlock target) {
        return this.startDateTime.isBefore(target.startDateTime)
                && this.endDateTime.isEqual(target.endDateTime);
    }

    private boolean isCover(TimeBlock target) {
        return this.startDateTime.isBefore(target.startDateTime)
                && this.endDateTime.isAfter(target.endDateTime);
    }

    private boolean isOverlappedForwardBy(TimeBlock target) {
        return this.startDateTime.isAfter(target.startDateTime)
                && this.endDateTime.isAfter(target.endDateTime)
                && this.startDateTime.isBefore(target.endDateTime);
    }

    private boolean isOverlappedBackwardBy(TimeBlock target) {
        return this.startDateTime.isBefore(target.startDateTime)
        && this.endDateTime.isBefore(target.endDateTime)
        && this.endDateTime.isAfter(target.startDateTime);
    }

    private boolean isApart(TimeBlock target) {
        return (this.startDateTime.isAfter(target.endDateTime) && this.endDateTime.isAfter(target.startDateTime))
                || (this.endDateTime.isBefore(target.startDateTime) && this.startDateTime.isBefore(target.endDateTime));
    }

    public boolean isContinue(TimeBlock target) {
        return (this.startDateTime.isEqual(target.endDateTime) && this.endDateTime.isAfter(target.startDateTime))
                || (this.endDateTime.isEqual(target.startDateTime) && this.startDateTime.isBefore(target.endDateTime));
    }

    public boolean isIncludeIn(TimeBlock target) {
        return this.startDateTime.isAfter(target.startDateTime)
                && this.endDateTime.isBefore(target.endDateTime);
    }

    public boolean isIncludeIn(List<TimeBlock> target) {
        return target.stream()
                .anyMatch(this::isIncludeIn);
    }


    private TimeBlock remainBackward(TimeBlock target) {
        return new TimeBlock(target.endDateTime, this.endDateTime);
    }

    private TimeBlock remainForward(TimeBlock target) {
        return new TimeBlock(this.startDateTime, target.startDateTime);
    }

    private List<TimeBlock> separate(TimeBlock target) {
        TimeBlock timeBlock1 = new TimeBlock(this.startDateTime, target.startDateTime);
        TimeBlock timeBlock2 = new TimeBlock(target.endDateTime, this.endDateTime);
        return List.of(timeBlock1, timeBlock2);
    }

    public int compareTo(TimeBlock o) {
        int compare = this.startDateTime.compareTo(o.startDateTime);
        if (compare == 0) {
            this.endDateTime.compareTo(o.endDateTime);
        }
        return compare;
    }

    public boolean isOverLapped(TimeBlock event) {
        return !(isApart(event) || isContinue(event));
    }

    public TimeBlock extractIntersection(TimeBlock target) {
        if (this.equals(target)) {
            return new TimeBlock(this.startDateTime, this.endDateTime);
        } else if (this.startDateTime.compareTo(target.startDateTime) <= 0
                && (this.endDateTime.compareTo(target.startDateTime) > 0
                    && this.endDateTime.compareTo(target.endDateTime) < 0)) {
            return new TimeBlock(target.startDateTime, this.endDateTime);
        } else if ((this.startDateTime.compareTo(target.startDateTime) > 0
                && this.startDateTime.compareTo(target.endDateTime) < 0)
                && this.endDateTime.compareTo(target.endDateTime) >= 0) {
            return new TimeBlock(this.startDateTime, target.endDateTime);
        } else {
            return null;
        }
    }
}

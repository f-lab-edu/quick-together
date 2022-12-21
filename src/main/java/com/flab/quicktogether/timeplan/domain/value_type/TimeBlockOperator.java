package com.flab.quicktogether.timeplan.domain.value_type;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeBlockOperator {

    private final List<OperableTimeBlock> timeBlocks = new ArrayList<>();

    public TimeBlockOperator append(List<TimeBlock> timeBlocks) {
        List<OperableTimeBlock> operableTimeBlocks = timeBlocks.stream().map(timeBlock -> OperableTimeBlock.convertFrom(timeBlock)).toList();
        this.timeBlocks.addAll(operableTimeBlocks);
        return this;
    }

    public TimeBlockOperator append(TimeBlock timeBlock) {
        OperableTimeBlock operableTimeBlock = OperableTimeBlock.convertFrom(timeBlock);
        this.timeBlocks.add(operableTimeBlock);
        return this;
    }

//    public TimeBlockOperator trim(TimeBlock timeBlock) {
//        OperableTimeBlock operableTimeBlock = OperableTimeBlock.convertFrom(timeBlock);
//
//            ArrayList<TimeBlock> trimmedBlocks = new ArrayList<>();
//
//            if (this.equals(target) || this.isIncludeIn(target)) {
//            } else if (this.isOverlappedWithStartEqualBy(target) || this.isOverlappedForwardBy(target)) {
//                trimmedBlocks.add(this.remainBackward(target));
//
//            } else if (this.isOverlappedWithEndEqualBy(target)|| this.isOverlappedBackwardBy(target)) {
//                trimmedBlocks.add(this.remainForward(target));
//
//            } else if (this.isCover(target)) {
//                trimmedBlocks.addAll(separate(target));
//
//            } else if (this.isApart(target)) {
//                trimmedBlocks.add(this);
//
//            } else if (this.isContinue(target)) {
//                trimmedBlocks.add(this);
//            }
//            return trimmedBlocks;
//        }
//
//        return this;
//    }

//    public TimeBlockOperator trim(List<TimeBlock> target) {
//        return this;
//    }
//
//    private void remainBackward(OperableTimeBlock remain, OperableTimeBlock trim) {
//        remain.startDateTime = trim.endDateTime;
//    }
//
//    private void remainForward(OperableTimeBlock remain, OperableTimeBlock trim) {
//        remain.endDateTime = trim.startDateTime;
//    }
//
//    private void separate(OperableTimeBlock remain, OperableTimeBlock trim) {
//        remain.endDateTime = trim.startDateTime;
//
//        OperableTimeBlock otherTrimmedBlock= new OperableTimeBlock(trim.endDateTime, remain.endDateTime);
//        this.timeBlocks.add(otherTrimmedBlock);
//    }

    @Getter @Setter
    static class OperableTimeBlock implements Comparable<OperableTimeBlock> {
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;

        private OperableTimeBlock(LocalDateTime startDateTime, LocalDateTime endDateTime) {
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
        }

        public static OperableTimeBlock convertFrom(TimeBlock timeBlock) {
            LocalDateTime startDateTime = timeBlock.getStartDateTime();
            LocalDateTime endDateTime = timeBlock.getEndDateTime();

            return new OperableTimeBlock(startDateTime, endDateTime);
        }


        private boolean isOverlappedWithStartEqualBy(OperableTimeBlock target) {
            return this.startDateTime.isEqual(target.startDateTime)
                    && this.endDateTime.isAfter(target.endDateTime);
        }

        private boolean isOverlappedWithEndEqualBy(OperableTimeBlock target) {
            return this.startDateTime.isBefore(target.startDateTime)
                    && this.endDateTime.isEqual(target.endDateTime);
        }

        private boolean isCover(OperableTimeBlock target) {
            return this.startDateTime.isBefore(target.startDateTime)
                    && this.endDateTime.isAfter(target.endDateTime);
        }

        private boolean isOverlappedForwardBy(OperableTimeBlock target) {
            return this.startDateTime.isAfter(target.startDateTime)
                    && this.endDateTime.isAfter(target.endDateTime)
                    && this.startDateTime.isBefore(target.endDateTime);
        }

        private boolean isOverlappedBackwardBy(OperableTimeBlock target) {
            return this.startDateTime.isBefore(target.startDateTime)
                    && this.endDateTime.isBefore(target.endDateTime)
                    && this.endDateTime.isAfter(target.startDateTime);
        }

        private boolean isApart(OperableTimeBlock target) {
            return (this.startDateTime.isAfter(target.endDateTime) && this.endDateTime.isAfter(target.startDateTime))
                    || (this.endDateTime.isBefore(target.startDateTime) && this.startDateTime.isBefore(target.endDateTime));
        }

        public boolean isContinue(OperableTimeBlock target) {
            return (this.startDateTime.isEqual(target.endDateTime) && this.endDateTime.isAfter(target.startDateTime))
                    || (this.endDateTime.isEqual(target.startDateTime) && this.startDateTime.isBefore(target.endDateTime));
        }

        public boolean isIncludeIn(OperableTimeBlock target) {
            return this.startDateTime.isAfter(target.startDateTime)
                    && this.endDateTime.isBefore(target.endDateTime);
        }


        @Override
        public int compareTo(OperableTimeBlock o) {
            int compare = this.startDateTime.compareTo(o.startDateTime);
            if (compare == 0) {
                compare = this.endDateTime.compareTo(o.endDateTime);
            }
            return compare;
        }

    }


}

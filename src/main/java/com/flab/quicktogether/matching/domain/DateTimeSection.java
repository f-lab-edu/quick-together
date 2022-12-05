package com.flab.quicktogether.matching.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Embeddable
public class DateTimeSection {
    @Column(name = "start_date_time")
    LocalDateTime startDateTime;
    @Column(name = "end_date_time")
    LocalDateTime endDateTime;

    public DateTimeSection() {
    }

    public DateTimeSection(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Optional<DateTimeSection> getOverLappingTime(DateTimeSection target) {
        if (this.isIncludeIn(target)) {
            return Optional.of(this);
        } else if (this.isOverlappingForward(target)) {
            return Optional.of(new DateTimeSection(target.startDateTime, this.endDateTime));
        } else if (this.isOverlappingBackward(target)) {
            return Optional.of(new DateTimeSection(this.startDateTime, target.endDateTime));
        } else return Optional.empty();
    }

// OverlappingTime -> startTime은 단위올림 최고 늦은 시작시간은 endTime-(event종료시간 - event시작시간)에서 단위 버림 -> 시작시간, 늦은 시작시간을 단위로 나누면 리스트완성
    public boolean isIncludeIn(DateTimeSection target) {
        if (this.startDateTime.isAfter(target.startDateTime) && this.endDateTime.isBefore(target.endDateTime)) {
            return true;
        }
        return false;
    }

    public boolean isOverlappingForward(DateTimeSection target) {
        if (this.startDateTime.isBefore(target.startDateTime) && this.endDateTime.isBefore(target.endDateTime)) {
            return true;
        }
        return false;
    }

    public boolean isOverlappingBackward(DateTimeSection target) {
        if (this.startDateTime.isAfter(target.startDateTime) && (this.endDateTime.isAfter(target.endDateTime))) {
            return true;
        }
        return false;
    }

    public boolean isSeparatedFrom(DateTimeSection target) {
        if (this.startDateTime.isAfter(target.endDateTime) || this.endDateTime.isBefore(target.startDateTime)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateTimeSection that)) return false;
        return getStartDateTime().equals(that.getStartDateTime()) && getEndDateTime().equals(that.getEndDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDateTime(), getEndDateTime());
    }

    @Override
    public String toString() {
        return "DateTimeSection{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}

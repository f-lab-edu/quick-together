package com.flab.quicktogether.timeplan.domain;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.exception.IllegalEventStateException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.presentation.EventUpdateRequestDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Entity
@ToString
@EqualsAndHashCode
public class Event implements Comparable<Event> {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_plan_id")
    private TimePlan timePlan;

    @Embedded
    private TimeBlock timeBlock;

    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;

    protected Event() {
    }

    public Event(TimePlan timePlan, String eventName, TimeBlock timeBlock) {
        this.eventName = nullProcess(eventName);
        this.timePlan = timePlan;

        verifyNotExpire(timeBlock);
        this.timeBlock = timeBlock;
        this.eventStatus = EventStatus.DEFAULT;
    }

    public void updateEvent(EventUpdateRequestDto eventUpdateRequestDto) {
        verifyModifiable();
        this.eventName = eventUpdateRequestDto.getEventName();
        this.timeBlock = new TimeBlock(eventUpdateRequestDto.getStartDateTime(), eventUpdateRequestDto.getEndDateTime());
    }

    public void delete() {
        verifyModifiable();
        this.eventStatus = EventStatus.DELETED;
    }

    private void verifyModifiable() {
        if (this.eventStatus.equals(EventStatus.DELETED)) {
            throw new IllegalEventStateException();
        }
    }

    private String nullProcess(String eventName) {
        if (eventName == null) {
            eventName = "CONFIDENTIAL";
        }
        return eventName;
    }

    private void verifyNotExpire(TimeBlock timeBlock) {
        boolean isExpired = timeBlock.getEndDateTime().compareTo(LocalDateTime.now()) <= 0;
        if (isExpired) {
            throw new ExpiredTimeException();
        }
    }

    @Override
    public int compareTo(Event o) {
        return this.timeBlock.compareTo(o.getTimeBlock());
    }
}

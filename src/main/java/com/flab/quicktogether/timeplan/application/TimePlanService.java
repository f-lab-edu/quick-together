package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.AbleRoutine;
import com.flab.quicktogether.timeplan.domain.Event;
import com.flab.quicktogether.timeplan.domain.exception.NoUniqueTimePlanCreateException;
import com.flab.quicktogether.timeplan.domain.exception.NotFoundEventException;
import com.flab.quicktogether.timeplan.domain.exception.NotFoundTimePlanException;
import com.flab.quicktogether.timeplan.domain.TimePlan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.infrastructure.EventRepository;
import com.flab.quicktogether.timeplan.infrastructure.TimePlanRepository;
import com.flab.quicktogether.timeplan.presentation.*;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TimePlanService {

    private final MemberRepository memberRepository;
    private final TimePlanRepository timePlanRepository;
    private final EventRepository eventRepository;

    public void registerTimePlan(Long memberId, TimePlanCreateRequestDto timePlanCreateRequestDto) {
        timePlanRepository.findByMemberId(memberId)
                .ifPresent(tp -> {throw new NoUniqueTimePlanCreateException();});
        Member member = memberRepository.findById(memberId).get();
        TimePlan timePlan = timePlanCreateRequestDto.toEntityOf(member);
        timePlanRepository.save(timePlan);
    }

    public void modifyAbleRoutine(Long memberId, List<AbleRoutineUpdateRequestDto> ableRoutineUpdateRequestDtos) {
        List<AbleRoutine> newAbleRoutine = ableRoutineUpdateRequestDtos.stream()
                .map(ableRoutineUpdateRequestDto -> ableRoutineUpdateRequestDto.toEntity())
                .toList();

        timePlanRepository.findByMemberId(memberId)
                .orElseThrow(NotFoundTimePlanException::new)
                .updateAbleRoutines(newAbleRoutine);

    }

    public void registerEvent(Long memberId, EventCreateRequestDto plannedEvent) {
        TimePlan timePlan = timePlanRepository.findByMemberId(memberId)
                .orElseThrow(NotFoundTimePlanException::new);
        Event event = plannedEvent.toEntity(timePlan);
        eventRepository.save(event);
    }

    public void editEvent(Long loginId, Long eventId,  EventUpdateRequestDto eventUpdateRequestDto) {
        eventRepository.findById(eventId)
                .orElseThrow(NotFoundEventException::new)
                .updateEvent(eventUpdateRequestDto);
    }

    public void removePlannedEvent(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(NotFoundEventException::new)
                .delete();
    }

    public TimePlanGetRequestDto getTimePlan(Long loginMemberId) {
        TimePlan timePlan = timePlanRepository.findByMemberId(loginMemberId)
                .orElseThrow(NotFoundTimePlanException::new);
        List<Event> reservedEvent = eventRepository.findEventsByTimePlanIdAfterCurrentTime(timePlan.getId());
        return TimePlanGetRequestDto.from(timePlan,reservedEvent);
    }

    public List<TimeBlock> getAvailableTime(Long MemberId, LocalDate startDate, LocalDate endDate) {
        TimePlan timePlan = timePlanRepository.findByMemberId(MemberId)
                .orElseThrow(NotFoundTimePlanException::new);
        List<Event> reservedEvent = eventRepository.findEventsByTimePlanIdAfterCurrentTime(timePlan.getId());

        return timePlan.extractAbleTimeBlock(reservedEvent, startDate, endDate);
    }

}

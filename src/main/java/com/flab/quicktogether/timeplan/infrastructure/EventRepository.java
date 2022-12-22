package com.flab.quicktogether.timeplan.infrastructure;

import com.flab.quicktogether.timeplan.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select e from Event e where e.timePlan.id = :timePlanId and e.eventStatus = :eventStatus and e.timeBlock.endDateTime > current_timestamp")
    List<Event> findEventsByTimePlanIdAfterCurrentTime(Long timePlanId);
}

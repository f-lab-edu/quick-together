package com.flab.quicktogether.meeting.infrastructure;

import com.flab.quicktogether.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}

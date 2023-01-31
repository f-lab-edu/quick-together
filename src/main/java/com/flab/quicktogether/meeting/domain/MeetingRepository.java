package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}

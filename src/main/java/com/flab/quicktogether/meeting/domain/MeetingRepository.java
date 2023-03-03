package com.flab.quicktogether.meeting.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findMeetingsByProjectAndMeetingStatus(Long projectId, MeetingStatus meetingStatus);

}

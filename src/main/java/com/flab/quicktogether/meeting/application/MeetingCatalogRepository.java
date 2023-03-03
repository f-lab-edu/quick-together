package com.flab.quicktogether.meeting.application;

import com.flab.quicktogether.meeting.presentation.MeetingCatalogDto;
import com.flab.quicktogether.meeting.presentation.MeetingSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.flab.quicktogether.meeting.domain.QMeeting.meeting;
import static com.flab.quicktogether.meeting.domain.QMeetingParticipant.meetingParticipant;

@Repository
public class MeetingCatalogRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MeetingCatalogRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;

    }

    public List<MeetingCatalogDto> search(Long memberId, MeetingSearchCondition meetingSearchCondition) {
        //이부분을 해결해주면 돼
        return queryFactory.select(Projections.fields(MeetingCatalogDto.class,
                        meeting.id,
                        meeting.project.projectName,
                        meeting.title,
                        meeting.timeBlock.startDateTime,
                        meeting.timeBlock.endDateTime))
                .from(meeting)
                .where(meetingParticipant.member.id.eq(memberId))
                .fetchAll().stream().toList();
    }
}

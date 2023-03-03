package com.flab.quicktogether.meeting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeeting is a Querydsl query type for Meeting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMeeting extends EntityPathBase<Meeting> {

    private static final long serialVersionUID = 1374599077L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeeting meeting = new QMeeting("meeting");

    public final StringPath description = createString("description");

    public final com.flab.quicktogether.member.domain.QMember host;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMeetingParticipants meetingParticipants;

    public final ListPath<MeetingProposal, QMeetingProposal> meetingProposals = this.<MeetingProposal, QMeetingProposal>createList("meetingProposals", MeetingProposal.class, QMeetingProposal.class, PathInits.DIRECT2);

    public final EnumPath<MeetingStatus> meetingStatus = createEnum("meetingStatus", MeetingStatus.class);

    public final com.flab.quicktogether.project.domain.QProject project;

    public final com.flab.quicktogether.timeplan.domain.value_type.QTimeBlock timeBlock;

    public final StringPath title = createString("title");

    public QMeeting(String variable) {
        this(Meeting.class, forVariable(variable), INITS);
    }

    public QMeeting(Path<? extends Meeting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeeting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeeting(PathMetadata metadata, PathInits inits) {
        this(Meeting.class, metadata, inits);
    }

    public QMeeting(Class<? extends Meeting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.host = inits.isInitialized("host") ? new com.flab.quicktogether.member.domain.QMember(forProperty("host")) : null;
        this.meetingParticipants = inits.isInitialized("meetingParticipants") ? new QMeetingParticipants(forProperty("meetingParticipants")) : null;
        this.project = inits.isInitialized("project") ? new com.flab.quicktogether.project.domain.QProject(forProperty("project"), inits.get("project")) : null;
        this.timeBlock = inits.isInitialized("timeBlock") ? new com.flab.quicktogether.timeplan.domain.value_type.QTimeBlock(forProperty("timeBlock")) : null;
    }

}


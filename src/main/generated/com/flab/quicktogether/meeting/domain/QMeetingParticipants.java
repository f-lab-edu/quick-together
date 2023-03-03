package com.flab.quicktogether.meeting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingParticipants is a Querydsl query type for MeetingParticipants
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMeetingParticipants extends BeanPath<MeetingParticipants> {

    private static final long serialVersionUID = -724388891L;

    public static final QMeetingParticipants meetingParticipants = new QMeetingParticipants("meetingParticipants");

    public final ListPath<MeetingParticipant, QMeetingParticipant> meetingParticipantList = this.<MeetingParticipant, QMeetingParticipant>createList("meetingParticipantList", MeetingParticipant.class, QMeetingParticipant.class, PathInits.DIRECT2);

    public QMeetingParticipants(String variable) {
        super(MeetingParticipants.class, forVariable(variable));
    }

    public QMeetingParticipants(Path<? extends MeetingParticipants> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMeetingParticipants(PathMetadata metadata) {
        super(MeetingParticipants.class, metadata);
    }

}


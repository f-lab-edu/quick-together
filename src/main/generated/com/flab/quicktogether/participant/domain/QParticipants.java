package com.flab.quicktogether.participant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipants is a Querydsl query type for Participants
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QParticipants extends BeanPath<Participants> {

    private static final long serialVersionUID = 2119613934L;

    public static final QParticipants participants1 = new QParticipants("participants1");

    public final ListPath<Participant, QParticipant> participants = this.<Participant, QParticipant>createList("participants", Participant.class, QParticipant.class, PathInits.DIRECT2);

    public QParticipants(String variable) {
        super(Participants.class, forVariable(variable));
    }

    public QParticipants(Path<? extends Participants> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipants(PathMetadata metadata) {
        super(Participants.class, metadata);
    }

}


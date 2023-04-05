package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAvailablePlan is a Querydsl query type for AvailablePlan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAvailablePlan extends EntityPathBase<AvailablePlan> {

    private static final long serialVersionUID = -140131346L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAvailablePlan availablePlan = new QAvailablePlan("availablePlan");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.flab.quicktogether.timeplan.domain.value_type.QRegularTimeBlock regularTimeBlock;

    public QAvailablePlan(String variable) {
        this(AvailablePlan.class, forVariable(variable), INITS);
    }

    public QAvailablePlan(Path<? extends AvailablePlan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAvailablePlan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAvailablePlan(PathMetadata metadata, PathInits inits) {
        this(AvailablePlan.class, metadata, inits);
    }

    public QAvailablePlan(Class<? extends AvailablePlan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.regularTimeBlock = inits.isInitialized("regularTimeBlock") ? new com.flab.quicktogether.timeplan.domain.value_type.QRegularTimeBlock(forProperty("regularTimeBlock")) : null;
    }

}


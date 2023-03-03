package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWeeklyAvailablePlan is a Querydsl query type for WeeklyAvailablePlan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeeklyAvailablePlan extends EntityPathBase<WeeklyAvailablePlan> {

    private static final long serialVersionUID = -1619470355L;

    public static final QWeeklyAvailablePlan weeklyAvailablePlan = new QWeeklyAvailablePlan("weeklyAvailablePlan");

    public final ListPath<AvailablePlan, QAvailablePlan> availablePlans = this.<AvailablePlan, QAvailablePlan>createList("availablePlans", AvailablePlan.class, QAvailablePlan.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QWeeklyAvailablePlan(String variable) {
        super(WeeklyAvailablePlan.class, forVariable(variable));
    }

    public QWeeklyAvailablePlan(Path<? extends WeeklyAvailablePlan> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeeklyAvailablePlan(PathMetadata metadata) {
        super(WeeklyAvailablePlan.class, metadata);
    }

}


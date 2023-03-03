package com.flab.quicktogether.timeplan.domain.value_type;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegularTimeBlock is a Querydsl query type for RegularTimeBlock
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRegularTimeBlock extends BeanPath<RegularTimeBlock> {

    private static final long serialVersionUID = 133401303L;

    public static final QRegularTimeBlock regularTimeBlock = new QRegularTimeBlock("regularTimeBlock");

    public final EnumPath<java.time.DayOfWeek> dayOfWeek = createEnum("dayOfWeek", java.time.DayOfWeek.class);

    public final TimePath<java.time.LocalTime> endTime = createTime("endTime", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public QRegularTimeBlock(String variable) {
        super(RegularTimeBlock.class, forVariable(variable));
    }

    public QRegularTimeBlock(Path<? extends RegularTimeBlock> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegularTimeBlock(PathMetadata metadata) {
        super(RegularTimeBlock.class, metadata);
    }

}


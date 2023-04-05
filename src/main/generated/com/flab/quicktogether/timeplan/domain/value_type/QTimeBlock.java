package com.flab.quicktogether.timeplan.domain.value_type;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimeBlock is a Querydsl query type for TimeBlock
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTimeBlock extends BeanPath<TimeBlock> {

    private static final long serialVersionUID = -1458356275L;

    public static final QTimeBlock timeBlock = new QTimeBlock("timeBlock");

    public final DateTimePath<java.time.LocalDateTime> endDateTime = createDateTime("endDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startDateTime = createDateTime("startDateTime", java.time.LocalDateTime.class);

    public QTimeBlock(String variable) {
        super(TimeBlock.class, forVariable(variable));
    }

    public QTimeBlock(Path<? extends TimeBlock> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimeBlock(PathMetadata metadata) {
        super(TimeBlock.class, metadata);
    }

}


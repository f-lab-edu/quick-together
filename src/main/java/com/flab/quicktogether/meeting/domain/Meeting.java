package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.timeplan.domain.Event;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
public class Meeting {

    @Id
    @GeneratedValue
    @Column(name = "meeting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Lob
    private String description;

    @Embedded
    private TimeBlock timeBlock;

    public Meeting(Project project, String description, TimeBlock timeBlock) {
        this.project = project;
        this.description = description;
        this.timeBlock = timeBlock;
    }
}

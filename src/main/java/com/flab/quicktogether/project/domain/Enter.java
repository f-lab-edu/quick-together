package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.event.Events;
import com.flab.quicktogether.project.event.ProjectJoinEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.flab.quicktogether.project.domain.ProjectJoinStatus.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enter {

    @Id
    @GeneratedValue
    @Column(name = "enter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterMember_id")
    private Member enterMember;

    @Enumerated(EnumType.STRING)
    private ProjectJoinStatus enterStatus;

    public Enter(Project project, Member enterMember) {
        this.project = project;
        this.enterMember = enterMember;
    }

    public static Enter enterMember(Project project, Member enterMember){
        Enter enter = new Enter(project, enterMember);
        enter.enterStatus = WAIT;
        return enter;
    }

    public void accept() {
        this.enterStatus = ACCEPT;
        Events.raise(new ProjectJoinEvent(project.getId(), enterMember.getId()));
    }

    public void reject() {
        this.enterStatus = REJECT;
    }
}

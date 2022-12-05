package com.flab.quicktogether.matching.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class MemberPlannedEvent {
    @Id @GeneratedValue
    Long id;

    @Embedded
    DateTimeSection dateTimeSection;

}

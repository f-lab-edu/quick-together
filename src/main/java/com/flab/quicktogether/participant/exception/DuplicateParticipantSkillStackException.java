package com.flab.quicktogether.participant.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateParticipantSkillStackException extends ApplicationException {

    public DuplicateParticipantSkillStackException() {
        super("DuplicateParticipantSkillStackException", HttpStatus.BAD_REQUEST);
    }
}

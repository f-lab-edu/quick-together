package com.flab.quicktogether.participant.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateParticipantPositionException extends ApplicationException {
    public DuplicateParticipantPositionException() {
        super("DuplicateParticipantPositionException", HttpStatus.BAD_REQUEST);
    }
}


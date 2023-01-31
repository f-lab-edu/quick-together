package com.flab.quicktogether.participant.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotAuthorizedParticipantException extends ApplicationException {
    public NotAuthorizedParticipantException() {
        super("NotAuthorizedParticipantException", HttpStatus.UNAUTHORIZED);
    }
}

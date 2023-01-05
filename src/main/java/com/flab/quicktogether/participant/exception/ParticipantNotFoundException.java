package com.flab.quicktogether.participant.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ParticipantNotFoundException extends ApplicationException {
    public ParticipantNotFoundException() {
        this.ERROR_CODE="ParticipantNotFoundException";
        this.HTTP_STATUS=HttpStatus.NOT_FOUND;
    }
}

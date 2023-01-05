package com.flab.quicktogether.participant.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import com.flab.quicktogether.globalsetting.domain.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicateParticipantPositionException extends ApplicationException {
    public DuplicateParticipantPositionException() {
        this.ERROR_CODE="DuplicateParticipantPositionException";
        this.HTTP_STATUS= HttpStatus.BAD_REQUEST;
    }
}

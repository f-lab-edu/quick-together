package com.flab.quicktogether.meeting.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AlreadyDeniedMeetingException extends ApplicationException {
    public AlreadyDeniedMeetingException() {
        super("AlreadyDeniedMeetingException", HttpStatus.BAD_REQUEST);
    }
}

package com.flab.quicktogether.meeting.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AlreadyApprovedMeetingException extends ApplicationException {
    public AlreadyApprovedMeetingException() {
        super("AlreadyApprovedMeetingException", HttpStatus.BAD_REQUEST);
    }
}

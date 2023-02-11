package com.flab.quicktogether.meeting.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MeetingNotFoundException extends ApplicationException {
    public MeetingNotFoundException() {
        super("MeetingNotFoundException", HttpStatus.NOT_FOUND);
    }
}

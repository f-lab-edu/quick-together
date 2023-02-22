package com.flab.quicktogether.meeting.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MeetingPostIllegalStateException extends ApplicationException {

    public MeetingPostIllegalStateException() {
        super("MeetingPostIllegalStateException", HttpStatus.BAD_REQUEST);
    }
}

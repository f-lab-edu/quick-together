package com.flab.quicktogether.meeting.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MeetingParticipantNotfoundException extends ApplicationException {

    public MeetingParticipantNotfoundException() {
        super("MeetingParticipantNotfoundException", HttpStatus.NOT_FOUND);
    }
}

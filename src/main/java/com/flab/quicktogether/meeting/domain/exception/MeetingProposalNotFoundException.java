package com.flab.quicktogether.meeting.domain.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MeetingProposalNotFoundException extends ApplicationException {

    public MeetingProposalNotFoundException() {
        super("MeetingProposalNotFoundException", HttpStatus.NOT_FOUND);
    }
}

package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateProjectParticipationException extends ApplicationException {
    public DuplicateProjectParticipationException() {
        super("DuplicateProjectParticipationException", HttpStatus.BAD_REQUEST);
    }
}

package com.flab.quicktogether.project.support.invite.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateInviteMemberException extends ApplicationException {
    public DuplicateInviteMemberException() {
        super("DuplicateInviteMemberException", HttpStatus.BAD_REQUEST);
    }
}
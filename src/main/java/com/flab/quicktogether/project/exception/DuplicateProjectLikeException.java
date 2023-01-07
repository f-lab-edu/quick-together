package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateProjectLikeException extends ApplicationException {
    public DuplicateProjectLikeException() {
        super("DuplicateProjectLikeException", HttpStatus.BAD_REQUEST);
    }
}

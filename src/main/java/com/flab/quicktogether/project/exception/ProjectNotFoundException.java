package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends ApplicationException {
    public ProjectNotFoundException() {
        super("ProjectNotFoundException", HttpStatus.NOT_FOUND);
    }
}

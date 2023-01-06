package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends ApplicationException {

    public ProjectNotFoundException() {
        this.ERROR_CODE = "ProjectNotFoundException";
        this.HTTP_STATUS = HttpStatus.NOT_FOUND;
    }
}

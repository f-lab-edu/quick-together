package com.flab.quicktogether.project.exception;

import com.flab.quicktogether.globalsetting.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateProjectSkillStackException extends ApplicationException {
    public DuplicateProjectSkillStackException() {
        this.ERROR_CODE = "DuplicateProjectSkillStackException";
        this.HTTP_STATUS = HttpStatus.BAD_REQUEST;
    }
}
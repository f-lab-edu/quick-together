package com.flab.quicktogether.project.exception;

public class DuplicateProjectSkillStackException extends ApplicationException{
    public DuplicateProjectSkillStackException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateProjectSkillStackException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DuplicateProjectSkillStackException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DuplicateProjectSkillStackException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}

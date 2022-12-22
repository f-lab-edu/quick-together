package com.flab.quicktogether.project.exception;

public class DuplicateParticipantSkillStackException extends RuntimeException {
    public DuplicateParticipantSkillStackException() {
    }

    public DuplicateParticipantSkillStackException(String message) {
        super(message);
    }

    public DuplicateParticipantSkillStackException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateParticipantSkillStackException(Throwable cause) {
        super(cause);
    }

    public DuplicateParticipantSkillStackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

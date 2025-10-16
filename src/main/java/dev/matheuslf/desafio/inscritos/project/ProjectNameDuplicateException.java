package dev.matheuslf.desafio.inscritos.project;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class ProjectNameDuplicateException extends BaseExceptionRunTime {

    public ProjectNameDuplicateException() {
    }

    public ProjectNameDuplicateException(String message) {
        super(message);
    }

    public ProjectNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNameDuplicateException(Throwable cause) {
        super(cause);
    }

    public ProjectNameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

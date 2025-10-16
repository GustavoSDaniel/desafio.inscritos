package dev.matheuslf.desafio.inscritos.project;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class ProjectNotFoundException extends BaseExceptionRunTime {
    public ProjectNotFoundException() {
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

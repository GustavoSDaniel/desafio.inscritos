package dev.matheuslf.desafio.inscritos.project;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class ProjectNameNotFoundException extends BaseExceptionRunTime {


    public ProjectNameNotFoundException() {
    }

    public ProjectNameNotFoundException(String message) {
        super(message);
    }

    public ProjectNameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNameNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProjectNameNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

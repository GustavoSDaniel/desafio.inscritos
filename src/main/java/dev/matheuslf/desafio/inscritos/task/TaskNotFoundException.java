package dev.matheuslf.desafio.inscritos.task;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class TaskNotFoundException extends BaseExceptionRunTime {

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }

    public TaskNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

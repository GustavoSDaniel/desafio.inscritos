package dev.matheuslf.desafio.inscritos.task;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class TaskNameDuplicateException extends BaseExceptionRunTime {

    public TaskNameDuplicateException() {
    }

    public TaskNameDuplicateException(String message) {
        super(message);
    }

    public TaskNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNameDuplicateException(Throwable cause) {
        super(cause);
    }

    public TaskNameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

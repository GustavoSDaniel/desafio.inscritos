package dev.matheuslf.desafio.inscritos.task;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class TaskStatusDoneException extends BaseExceptionRunTime {
    public TaskStatusDoneException() {
    }

    public TaskStatusDoneException(String message) {
        super(message);
    }

    public TaskStatusDoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskStatusDoneException(Throwable cause) {
        super(cause);
    }

    public TaskStatusDoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

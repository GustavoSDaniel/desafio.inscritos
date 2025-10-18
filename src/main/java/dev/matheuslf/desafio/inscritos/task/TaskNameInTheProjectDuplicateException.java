package dev.matheuslf.desafio.inscritos.task;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class TaskNameInTheProjectDuplicateException extends BaseExceptionRunTime {

    public TaskNameInTheProjectDuplicateException() {
    }

    public TaskNameInTheProjectDuplicateException(String message) {
        super(message);
    }

    public TaskNameInTheProjectDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNameInTheProjectDuplicateException(Throwable cause) {
        super(cause);
    }

    public TaskNameInTheProjectDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package dev.matheuslf.desafio.inscritos.task;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionBusiness;

public class TaskNameTooShortException extends BaseExceptionBusiness {
    public TaskNameTooShortException() {
    }

    public TaskNameTooShortException(String message) {
        super(message);
    }

    public TaskNameTooShortException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNameTooShortException(Throwable cause) {
        super(cause);
    }

    public TaskNameTooShortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

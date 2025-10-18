package dev.matheuslf.desafio.inscritos.util;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class DateEndBeforeStarDateException extends BaseExceptionRunTime {

    public DateEndBeforeStarDateException() {
    }

    public DateEndBeforeStarDateException(String message) {
        super(message);
    }

    public DateEndBeforeStarDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateEndBeforeStarDateException(Throwable cause) {
        super(cause);
    }

    public DateEndBeforeStarDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

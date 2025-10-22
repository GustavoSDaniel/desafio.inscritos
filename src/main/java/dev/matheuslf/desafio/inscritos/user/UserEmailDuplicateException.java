package dev.matheuslf.desafio.inscritos.user;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionRunTime;

public class UserEmailDuplicateException extends BaseExceptionRunTime {

    public UserEmailDuplicateException() {
    }

    public UserEmailDuplicateException(String message) {
        super(message);
    }

    public UserEmailDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserEmailDuplicateException(Throwable cause) {
        super(cause);
    }

    public UserEmailDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

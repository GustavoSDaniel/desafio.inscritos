package dev.matheuslf.desafio.inscritos.user;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionBusiness;

public class UserNameTooShortException extends BaseExceptionBusiness {

    public UserNameTooShortException() {
    }

    public UserNameTooShortException(String message) {
        super(message);
    }

    public UserNameTooShortException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameTooShortException(Throwable cause) {
        super(cause);
    }

    public UserNameTooShortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package dev.matheuslf.desafio.inscritos.project;

import dev.matheuslf.desafio.inscritos.exception.BaseExceptionBusiness;

public class ProjectNameTooShortException extends BaseExceptionBusiness {

    public ProjectNameTooShortException() {
    }

    public ProjectNameTooShortException(String message) {
        super(message);
    }

    public ProjectNameTooShortException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNameTooShortException(Throwable cause) {
        super(cause);
    }

    public ProjectNameTooShortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

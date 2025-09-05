package com.semenihin.exceptions;

public class LBDaoException extends RuntimeException {
    public LBDaoException(String message) {
        super(message);
    }

    public LBDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public LBDaoException(Throwable cause) {
        super(cause);
    }

    public LBDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LBDaoException() {
    }
}

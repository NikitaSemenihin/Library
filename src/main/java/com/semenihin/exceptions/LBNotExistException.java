package com.semenihin.exceptions;

public class LBNotExistException extends RuntimeException {
    public LBNotExistException(String message) {
        super(message);
    }
}

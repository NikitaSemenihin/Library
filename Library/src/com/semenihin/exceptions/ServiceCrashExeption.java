package com.semenihin.exceptions;

public class ServiceCrashExeption extends RuntimeException {
    public ServiceCrashExeption(String message) {
        super(message);
    }

    public ServiceCrashExeption(Throwable cause) {
        super(cause);
    }
}

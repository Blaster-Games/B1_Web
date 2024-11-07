package org.kdr.blaster.util;

public class CustomJWTException extends RuntimeException {
    public CustomJWTException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

package org.deliveroo.exception;

public class CronException extends RuntimeException {
    public CronException(String message) {
        super(message);
    }
}
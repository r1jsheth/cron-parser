package org.deliveroo.exception;

public class CronParseException extends RuntimeException {
    public CronParseException(String message) {
        super(message);
    }
}
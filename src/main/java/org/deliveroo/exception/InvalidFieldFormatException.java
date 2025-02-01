package org.deliveroo.exception;

public class InvalidFieldFormatException extends CronException {
    public InvalidFieldFormatException(String field, String value) {
        super(String.format("Invalid format in %s field: '%s'", field, value));
    }
}
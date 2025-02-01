package org.deliveroo.exception;

public class InvalidFieldValueException extends CronException {
    public InvalidFieldValueException(String field, String value) {
        super(String.format("Invalid value '%s' for %s field", value, field));
    }
}
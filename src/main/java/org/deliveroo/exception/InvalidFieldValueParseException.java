package org.deliveroo.exception;

public class InvalidFieldValueParseException extends CronParseException {
    public InvalidFieldValueParseException(String field, String value) {
        super(String.format("Invalid value '%s' for %s field", value, field));
    }
}
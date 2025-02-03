package org.deliveroo.exception;

public class InvalidFieldFormatParseException extends CronParseException {
    public InvalidFieldFormatParseException(String field, String value) {
        super(String.format("Invalid format in %s field: '%s'", field, value));
    }
}
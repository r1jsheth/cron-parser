package org.deliveroo.model;

import org.deliveroo.exception.InvalidFieldFormatException;
import org.deliveroo.exception.InvalidFieldValueException;
public class CronFieldParser {
    private final CronField field;

    public CronFieldParser(CronField field) {
        this.field = field;
    }
    private void validateValue(int value) {
        if (value < field.getMin() || value > field.getMax()) {
            throw new InvalidFieldValueException(
                    field.getDisplayName(),
                    String.format("%d (allowed range: %d-%d)", value, field.getMin(), field.getMax())
            );
        }
    }

    private void parseStep(String expression, Set<Integer> values) {
        String[] parts = expression.split("/");
        if (parts.length != 2) {
            throw new InvalidFieldFormatException(field.getDisplayName(), expression);
        }

        int step = Integer.parseInt(parts[1]);
        if (step <= 0) {
            throw new InvalidFieldValueException(field.getDisplayName(), "step value must be positive");
        }

        int start = field.getMin();
        int end = field.getMax();

        if (!parts[0].equals("*")) {
            if (parts[0].contains("-")) {
                String[] rangeParts = parts[0].split("-");
                start = Integer.parseInt(rangeParts[0]);
                end = Integer.parseInt(rangeParts[1]);
                validateValue(start);
                validateValue(end);
                if (start > end) {
                    throw new InvalidFieldValueException(field.getDisplayName(), "invalid range: start > end");
                }
            } else {
                start = Integer.parseInt(parts[0]);
                validateValue(start);
            }
        }

        for (int i = start; i <= end; i += step) {
            values.add(i);
        }
    }

    private void parseRange(String expression, Set<Integer> values) {
        String[] parts = expression.split("-");
        if (parts.length != 2) {
            throw new InvalidFieldFormatException(field.getDisplayName(), expression);
        }

        int start = Integer.parseInt(parts[0]);
        int end = Integer.parseInt(parts[1]);

        validateValue(start);
        validateValue(end);

        if (start > end) {
            throw new InvalidFieldValueException(field.getDisplayName(), "invalid range: start > end");
        }

        for (int i = start; i <= end; i++) {
            values.add(i);
        }
    }
}

package org.deliveroo.model;

public class CronFieldParser {
    private void validateValue(int value) {
    }
    private void parseStep(String expression, Set<Integer> values) {

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

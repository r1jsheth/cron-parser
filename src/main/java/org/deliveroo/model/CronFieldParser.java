package org.deliveroo.model;

public class CronFieldParser {
    private void validateValue(int value) {
    }
    private void parseRange(String expression, Set<Integer> values) {
        String[] parts = expression.split("-");

        int start = Integer.parseInt(parts[0]);
        int end = Integer.parseInt(parts[1]);

        validateValue(start);
        validateValue(end);
        for (int i = start; i <= end; i++) {
            values.add(i);
        }
    }
}

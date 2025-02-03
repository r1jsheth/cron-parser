package org.deliveroo.model;

import org.deliveroo.exception.CronException;
import org.deliveroo.exception.InvalidFieldValueException;
import org.deliveroo.exception.InvalidFieldFormatException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class CronExpression {
    private final Map<CronField, Set<Integer>> fieldValues;
    private final String command;
    private static final String USAGE_INFO = """
        Valid cron expression format:
        Field         Allowed Values    Special Characters
        -----         --------------    ------------------
        Minute        0-59             * , - /
        Hour          0-23             * , - /
        Day of Month  1-31             * , - /
        Month         1-12             * , - /
        Day of Week   1-7              * , - /
        Command       Any valid command (optional)
        
        Examples:
        */15 0 1,15 * 1-5 /usr/bin/find
        0 2 * * * /scripts/backup.sh
        """;

    public static String getUsageInfo() {
        return USAGE_INFO;
    }

    public CronExpression(String cronString) {
        String[] parts = cronString.trim().split("\\s+");
        if (parts.length != 5 && parts.length != 6) {
            throw new CronException(
                    "Invalid number of fields. Expected 5 or 6 fields, found: " + parts.length +
                            "\nFormat: minute hour day-of-month month day-of-week [command]"
            );
        }

        fieldValues = new EnumMap<>(CronField.class);
        int i = 0;
        for (CronField field : CronField.values()) {
            try {
                CronFieldParser parser = new CronFieldParser(field);
                fieldValues.put(field, parser.parse(parts[i++]));
            } catch (NumberFormatException e) {
                throw new InvalidFieldValueException(field.getDisplayName(), parts[i-1]);
            } catch (IllegalArgumentException e) {
                throw new InvalidFieldFormatException(field.getDisplayName(), parts[i-1]);
            }
        }
        this.command = parts.length == 6 ? parts[5] : "";
    }

    public String format() {
        StringBuilder result = new StringBuilder();
        for (CronField field : CronField.values()) {
            result.append(String.format("%-14s", field.getDisplayName()));
            fieldValues.get(field).forEach(value -> result.append(value).append(" "));
            result.append("\n");
        }
        if (!command.isEmpty()) {
            result.append(String.format("%-14s%s", "command", command));
        }
        return result.toString();
    }
}

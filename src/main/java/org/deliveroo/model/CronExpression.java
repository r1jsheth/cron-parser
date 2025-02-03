package org.deliveroo.model;

import org.deliveroo.exception.CronException;
import org.deliveroo.exception.InvalidFieldValueException;
import org.deliveroo.exception.InvalidFieldFormatException;
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
}

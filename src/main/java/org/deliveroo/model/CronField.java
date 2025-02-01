package org.deliveroo.model;

public enum CronField {
    MINUTE(0, 59, "minute"),
    HOUR(0, 23, "hour"),
    DAY_OF_MONTH(1, 31, "day of month"),
    MONTH(1, 12, "month"),
    DAY_OF_WEEK(1, 7, "day of week");

    private final int min;
    private final int max;
    private final String displayName;

    CronField(int min, int max, String displayName) {
        this.min = min;
        this.max = max;
        this.displayName = displayName;
    }

    public int getMin() { return min; }
    public int getMax() { return max; }
    public String getDisplayName() { return displayName; }
}

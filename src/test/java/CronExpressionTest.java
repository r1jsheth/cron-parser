import org.deliveroo.exception.CronParseException;
import org.deliveroo.exception.InvalidFieldFormatParseException;
import org.deliveroo.exception.InvalidFieldValueParseException;
import org.deliveroo.model.CronExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class CronExpressionTest {

    @Nested
    @DisplayName("Basic Format")
    class BasicFormatTests {
        @Test
        void testStandardSixFieldFormat() {
            String input = "*/15 0 1,15 * 1-5 /usr/bin/find";
            CronExpression cron = new CronExpression(input);
            String result = cron.format();

            String expected =
                    "minute        0 15 30 45 \n" +
                    "hour          0 \n" +
                    "day of month  1 15 \n" +
                    "month         1 2 3 4 5 6 7 8 9 10 11 12 \n" +
                    "day of week   1 2 3 4 5 \n" +
                    "command       /usr/bin/find";

            assertEquals(expected, result);
        }

        @Test
        void testFiveFieldFormat() {
            String input = "*/15 0 1,15 * 1-5";
            CronExpression cron = new CronExpression(input);
            String result = cron.format();

            String expected =
                    "minute        0 15 30 45 \n" +
                    "hour          0 \n" +
                    "day of month  1 15 \n" +
                    "month         1 2 3 4 5 6 7 8 9 10 11 12 \n" +
                    "day of week   1 2 3 4 5";

            assertEquals(expected, result.trim());
        }

        @Test
        void testInvalidFieldCount() {
            assertThrows(CronParseException.class, () ->
                new CronExpression("* * * *")
            );

            assertThrows(CronParseException.class, () ->
                new CronExpression("* * * * * * *")
            );
        }
    }

    @Nested
    @DisplayName("Field Value")
    class FieldValueTests {

        @Test
        void testMinuteValidation() {
            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("-1 * * * * /cmd"));

            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("60 * * * * /cmd"));
        }

        @Test
        void testHourValidation() {
            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("* 24 * * * /cmd"));

            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("* -1 * * * /cmd"));
        }

        @Test
        void testDayOfMonthValidation() {
            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("* * 32 * * /cmd"));
        }

        @Test
        void testMonthValidation() {
            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("* * * 13 * /cmd"));
        }

        @Test
        void testDayOfWeekValidation() {
            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("* * * * 8 /cmd"));
        }
    }

    @Nested
    @DisplayName("Special Character")
    class SpecialCharacterTests {

        @Test
        void testAsterisk() {
            CronExpression cron = new CronExpression("* * * * * /cmd");
            String result = cron.format();
            assertTrue(result.contains("minute        0 1 2 3 4 5 6 7 8 9 10")); // partial check
            assertTrue(result.contains("hour          0 1 2 3 4 5 6 7 8 9 10")); // partial check
        }

        @Test
        void testSteps() {
            CronExpression cron = new CronExpression("*/20 */6 * * * /cmd");
            String result = cron.format();
            assertEquals("0 20 40", extractValues(result, "minute"));
            assertEquals("0 6 12 18", extractValues(result, "hour"));
        }

        @Test
        void testStepWithRange() {
            CronExpression cron = new CronExpression("10-30/5 * * * * /cmd");
            String result = cron.format();
            assertEquals("10 15 20 25 30", extractValues(result, "minute"));
        }

        @Test
        void testInvalidStep() {
            assertThrows(InvalidFieldFormatParseException.class, () ->
                    new CronExpression("*/10/2 * * * * /cmd"));
        }

        @Test
        void testLists() {
            CronExpression cron = new CronExpression("1,5,10,15 * * * * /cmd");
            String result = cron.format();
            assertEquals("1 5 10 15", extractValues(result, "minute"));
        }

        @Test
        void testRanges() {
            CronExpression cron = new CronExpression("1-5 * * * * /cmd");
            String result = cron.format();
            assertEquals("1 2 3 4 5", extractValues(result, "minute"));
        }

        @Test
        void testInvalidRange() {
            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("5-1 * * * * /cmd"));
        }

        @Test
        void testComplexCombinations() {
            CronExpression cron = new CronExpression("1-5,10-15/2,30,45 * * * * /cmd");
            String result = cron.format();
            assertEquals("1 2 3 4 5 10 12 14 30 45", extractValues(result, "minute"));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCaseTests {

        @Test
        void testEmptyFields() {
            assertThrows(InvalidFieldValueParseException.class, () ->
                    new CronExpression("  * * * * /cmd"));
        }

        @Test
        void testExtraSpaces() {
            CronExpression cron = new CronExpression("*    *     *    *    *      /usr/bin/cmd");
            assertDoesNotThrow(() -> cron.format());
        }

        @Test
        void testMaxValues() {
            CronExpression cron = new CronExpression("59 23 31 12 7 /cmd");
            assertDoesNotThrow(() -> cron.format());
        }

        @Test
        void testMinValues() {
            CronExpression cron = new CronExpression("0 0 1 1 1 /cmd");
            assertDoesNotThrow(() -> cron.format());
        }

    }

    private String extractValues(String output, String fieldName) {
        for (String line : output.split("\n")) {
            if (line.startsWith(fieldName)) {
                return line.substring(14).trim();
            }
        }
        return "";
    }
}
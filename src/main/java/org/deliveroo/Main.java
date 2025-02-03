package org.deliveroo;

import org.deliveroo.exception.CronParseException;
import org.deliveroo.model.CronExpression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Cron Expression Parser!");
        System.out.println(CronExpression.getUsageInfo());

        while (true) {
            System.out.println("\n------------------------------");
            System.out.println("To exit enter 'x'");
            System.out.println("Enter cron expression (5 or 6 fields):");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("x")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }

            try {
                CronExpression cron = new CronExpression(input);
                System.out.println("\nParsed Expression:");
                System.out.println(cron.format());
            } catch (CronParseException e) {
                System.err.println("Error: " + e.getMessage());
                System.out.println("\nNeed help? Enter 'h' to show usage information");
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again with a valid cron expression");
            }

            if (input.equalsIgnoreCase("h")) {
                System.out.println(CronExpression.getUsageInfo());
            }
        }
    }
}
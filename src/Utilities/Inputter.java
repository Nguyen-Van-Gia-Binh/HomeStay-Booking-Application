package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Inputter {

    public static Scanner sc = new Scanner(System.in);

    public static String getString(String msg, String pattern, boolean canBeEmpty) {
        String input;
        do {
            System.out.print(msg);
            input = sc.nextLine().trim();

            if (canBeEmpty && input.isEmpty()) {
                return input;
            }

            if (!input.isEmpty()) {
                if (pattern == null || pattern.isEmpty() || Validator.isValid(input, pattern)) {
                    return input;
                }
            }

            System.out.println("Invalid input. Please try again.");
        } while (true);
    }

    public static int getInt(String msg) {
        int number;
        do {
            try {
                System.out.print(msg);
                number = Integer.parseInt(sc.nextLine().trim());
                if (number > 0) {
                    return number;
                }
                System.out.println("Number must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        } while (true);
    }

    public static int getInt(String msg, int min, int max) {
        int number;
        do {
            try {
                System.out.print(msg);
                number = Integer.parseInt(sc.nextLine().trim());
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        } while (true);
    }

    public static double getDouble(String msg) {
        double number;
        do {
            try {
                System.out.print(msg);
                number = Double.parseDouble(sc.nextLine().trim());
                if (number > 0) {
                    return number;
                }
                System.out.println("Number must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        } while (true);
    }

    public static LocalDate getLocalDate(String msg, boolean canBeEmpty) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        do {
            System.out.print(msg);
            String input = sc.nextLine().trim();

            if (canBeEmpty && input.isEmpty()) {
                return null;
            }

            if (!Validator.isValid(input, Validator.DATE_VALID)) {
                System.out.println("Invalid date format (dd/MM/yyyy).");
                continue;
            }
            try {
                date = LocalDate.parse(input, formatter);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date.");
            }
        } while (true);
    }

    public static boolean getYesNo(String msg) {
        String input;
        do {
            System.out.print(msg + " (Y/N): ");
            input = sc.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            }
            if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        } while (true);
    }
}

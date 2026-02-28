package utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Inputter class for getting input from the user
 */
public class Inputter {

    public static Scanner sc = new Scanner(System.in);

    /**
     * Get a string from the user
     * 
     * @param msg        The message to display
     * @param pattern    The pattern to validate the input
     * @param canBeEmpty Whether the input can be empty
     * @return The input string
     */
    public static String getString(String msg, String pattern, boolean canBeEmpty) {
        String input;
        do {
            // Print the message
            System.out.print(msg);
            // Get the input and trim it
            input = sc.nextLine().trim();

            // Check if the input can be empty
            if (canBeEmpty && input.isEmpty()) {
                return input;
            }

            // Check if the input is valid with regex
            if (!input.isEmpty()) {
                if (pattern == null || pattern.isEmpty() || Validator.isValid(input, pattern)) {
                    return input;
                }
            }

            System.out.println("Invalid input. Please try again.");
        } while (true);
    }

    /**
     * Get an positive integer from the user
     * 
     * @param msg        The message to display
     * @param canBeEmpty Whether the input can be empty
     * @return The input integer or 0 if empty
     */
    public static int getInt(String msg, boolean canBeEmpty) {
        int number;
        do {
            try {
                // Print the message
                System.out.print(msg);
                // Get the input and trim it
                String input = sc.nextLine().trim();
                // Check if the input can be empty
                if (canBeEmpty && input.isEmpty()) {
                    return 0;
                }
                number = Integer.parseInt(input);
                // Check if the number is positive
                if (number > 0) {
                    return number;
                }
                System.out.println("Number must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        } while (true);
    }

    /**
     * Get an integer from the user with min and max values
     * 
     * @param msg The message to display
     * @param min The minimum value
     * @param max The maximum value
     * @return The input integer
     */
    public static int getInt(String msg, int min, int max) {
        int number;
        do {
            try {
                // Print the message
                System.out.print(msg);
                // Get the input and trim it
                number = Integer.parseInt(sc.nextLine().trim());
                // Check if the number is within the range
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        } while (true);
    }

    /**
     * Get a positive double from the user
     * 
     * @param msg        The message to display
     * @param canBeEmpty Whether the input can be empty
     * @return The input double or 0 if empty
     */
    public static double getDouble(String msg, boolean canBeEmpty) {
        double number;
        do {
            try {
                // Print the message
                System.out.print(msg);
                // Get the input and trim it
                String input = sc.nextLine().trim();
                // Check if the input can be empty
                if (canBeEmpty && input.isEmpty()) {
                    return 0;
                }
                number = Double.parseDouble(input);
                // Check if the number is positive
                if (number > 0) {
                    return number;
                }
                System.out.println("Number must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        } while (true);
    }

    /**
     * Get a local date from the user
     * 
     * @param msg        The message to display
     * @param canBeEmpty Whether the input can be empty
     * @return The input date in dd/MM/yyyy format or null if empty
     */
    public static LocalDate getLocalDate(String msg, boolean canBeEmpty) {
        // Create a date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        do {
            // Print the message
            System.out.print(msg);
            // Get the input and trim it
            String input = sc.nextLine().trim();

            // Check if the input can be empty
            if (canBeEmpty && input.isEmpty()) {
                return null;
            }
            // Check if the input is valid with regex
            if (!Validator.isValid(input, Validator.DATE_VALID)) {
                System.out.println("Invalid date format (dd/MM/yyyy).");
                // Continue to the next iteration
                continue;
            }
            // Parse the input to a date
            try {
                date = LocalDate.parse(input, formatter);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date.");
            }
        } while (true);
    }

    /**
     * Get a yes/no answer from the user
     * 
     * @param msg The message to display
     * @return True if the answer is yes, false if the answer is no
     */
    public static boolean getYesNo(String msg) {
        String input;
        do {
            // Print the message
            System.out.print(msg + " (Y/N): ");
            // Get the input and trim it
            input = sc.nextLine().trim().toUpperCase();
            // Check if the input is yes
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            }
            // Check if the input is no
            if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            // Print the error message
            System.out.println("Please enter Y or N.");
        } while (true);
    }

    /**
     * Get a boolean from the user
     * 
     * @param msg        The message to display
     * @param canBeEmpty Whether the input can be empty
     * @return The input boolean or null if empty
     */
    public static Boolean getBoolean(String msg, boolean canBeEmpty) {
        do {
            // Print the message
            System.out.print(msg);
            // Get the input and trim it
            String input = sc.nextLine().trim().toUpperCase();
            // Check if the input can be empty
            if (canBeEmpty && input.isEmpty()) {
                return null;
            }
            // Check if the input is yes
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            }
            // Check if the input is no
            if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            // Print the error message
            System.out.println("Please enter Y or N.");
        } while (true);
    }
}

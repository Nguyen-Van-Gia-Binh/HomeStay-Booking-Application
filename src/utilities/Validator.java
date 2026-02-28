package utilities;

import java.time.format.DateTimeFormatter;

/**
 * Validator interface for validating input
 */
public interface Validator {
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // Regex patterns for validation
    String DATE_VALID = "^\\d{2}/\\d{2}/\\d{4}$";
    String PHONE_VALID = "^\\d{10}$";
    String HOME_ID_VALID = "^HS\\d{4}$";
    String BOOKING_ID_VALID = "^B\\d{5}$";
    String TOUR_ID_VALID = "^T\\d{5}$";

    /**
     * Validate input against a pattern
     * 
     * @param input   The input to validate
     * @param pattern The pattern to validate against
     * @return True if the input matches the pattern, false otherwise
     */
    static boolean isValid(String input, String pattern) {
        return input.matches(pattern);
    }

}

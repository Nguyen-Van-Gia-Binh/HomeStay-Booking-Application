package Utilities;

public interface Validator {
    String DATE_VALID = "^\\d{2}/\\d{2}/\\d{4}$";
    String PHONE_VALID = "^\\d{10}$";
    String HOME_ID_VALID = "^HS\\d{4}$";
    String BOOKING_ID_VALID = "^B\\d{5}$";

    static boolean isValid(String input, String pattern) {
        return input.matches(pattern);
    }

}

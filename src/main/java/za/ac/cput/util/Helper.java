package za.ac.cput.util;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class Helper {
    private Helper() {
        throw new AssertionError("ValidationHelper cannot be instantiated");
    }

    public static void requireNotEmptyOrNull(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    /**
     * Validates that a list is not null or empty
     */
    public static void requireNotEmptyOrNull(List<?> list, String fieldName) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    /**
     * Validates that an array is not null or empty
     */
    public static void requireNotEmptyOrNull(Object[] array, String fieldName) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    // ==================== NUMERIC VALIDATIONS ====================

    /**
     * Validates that a number is not negative
     */
    public static void requireNotNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative. Value: " + value);
        }
    }

    /**
     * Validates that a long is not negative
     */
    public static void requireNotNegative(long value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative. Value: " + value);
        }
    }

    /**
     * Validates that a double is not negative
     */
    public static void requireNotNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative. Value: " + value);
        }
    }

    /**
     * Validates that a number is positive (greater than 0)
     */
    public static void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive. Value: " + value);
        }
    }

    /**
     * Validates that a double is positive (greater than 0)
     */
    public static void requirePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive. Value: " + value);
        }
    }

    /**
     * Validates that a number is zero or positive
     */
    public static void requireZeroOrPositive(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative. Value: " + value);
        }
    }

    // ==================== OBJECT VALIDATIONS ====================

    /**
     * Validates that an object is not null
     */
    public static <T> T requireNonNull(T obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        return obj;
    }

    /**
     * Validates that an object is null
     */
    public static void requireNull(Object obj, String fieldName) {
        if (obj != null) {
            throw new IllegalArgumentException(fieldName + " must be null");
        }
    }

    public static boolean isValidEmail(String email) {
        return isValidEmailWithRegex(email);
    }

    public static boolean isValidEmailWithRegex(String email) {
        String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern PATTERN = Pattern.compile(REGEX_EMAIL);
        requireNotEmptyOrNull(email, "Email");
        if (!PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid Email" + email);
        }
        return true;
    }

    public static boolean isValidEmailWithApacheCommons(String email){
        requireNotEmptyOrNull(email, "Email");

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid Email format: " + email);
        }
        return true;
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime != null ? dateTime.format(formatter) : null;
    }
    // ==================== PHONE VALIDATIONS ====================

    /**
     * Validates South African phone number
     * Format: 0XXXXXXXXX (10 digits starting with 0) or +27XXXXXXXXX
     */
    public static boolean isValidSouthAfricanPhone(String phone) {
        requireNotEmptyOrNull(phone, "Phone number");

        // South African phone number regex
        // Landline: 0[1-9]{2}[0-9]{7}
        // Mobile: 0[6-8][0-9]{8}
        String phoneRegex = "^(\\+27|0)[0-9]{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);

        if (!pattern.matcher(phone).matches()) {
            throw new IllegalArgumentException("Invalid South African phone number format: " + phone +
                    ". Expected format: 0XXXXXXXXX or +27XXXXXXXXX");
        }
        return true;
    }

    /**
     * Validates South African mobile number specifically
     * Format: 0[6-8][0-9]{8}
     */
    public static boolean isValidSouthAfricanMobile(String phone) {
        requireNotEmptyOrNull(phone, "Mobile number");

        String mobileRegex = "^(\\+27|0)[6-8][0-9]{8}$";
        Pattern pattern = Pattern.compile(mobileRegex);

        if (!pattern.matcher(phone).matches()) {
            throw new IllegalArgumentException("Invalid South African mobile number format: " + phone +
                    ". Expected format: 0[6-8]XXXXXXXX or +27[6-8]XXXXXXXX");
        }
        return true;
    }

    // ==================== ID VALIDATIONS ====================

    /**
     * Validates South African ID number
     * Format: YYMMDD G S SS C (13 digits)
     * - YYMMDD: Date of birth
     * - G: Gender (0-4 female, 5-9 male)
     * - S: Citizenship (0 = SA citizen, 1 = permanent resident)
     * - SS: Sequence number
     * - C: Checksum using Luhn algorithm
     */
    public static boolean isValidSouthAfricanId(String idNumber) {
        requireNotEmptyOrNull(idNumber, "ID Number");

        // Check length
        if (idNumber.length() != 13) {
            throw new IllegalArgumentException("Invalid South African ID number: Must be exactly 13 digits. " +
                    "Provided: " + idNumber + " (Length: " + idNumber.length() + ")");
        }

        // Check if all characters are digits
        if (!idNumber.matches("\\d{13}")) {
            throw new IllegalArgumentException("Invalid South African ID number: Must contain only digits. " +
                    "Provided: " + idNumber);
        }

        // Extract components
        String yearPart = idNumber.substring(0, 2);
        String monthPart = idNumber.substring(2, 4);
        String dayPart = idNumber.substring(4, 6);
        String genderDigit = idNumber.substring(6, 7);
        String citizenshipDigit = idNumber.substring(10, 11);

        // Validate date of birth
        int year = Integer.parseInt(yearPart);
        int month = Integer.parseInt(monthPart);
        int day = Integer.parseInt(dayPart);

        // Check month (1-12)
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid South African ID number: Invalid month (" + month +
                    "). Month must be between 01 and 12.");
        }

        // Check day based on month
        int maxDay = getMaxDaysInMonth(month, year);
        if (day < 1 || day > maxDay) {
            throw new IllegalArgumentException("Invalid South African ID number: Invalid day (" + day +
                    ") for month " + month + ". Day must be between 01 and " + maxDay + ".");
        }

        // Validate gender digit (0-9)
        int gender = Integer.parseInt(genderDigit);
        if (gender < 0 || gender > 9) {
            throw new IllegalArgumentException("Invalid South African ID number: Invalid gender digit (" +
                    genderDigit + "). Must be between 0 and 9.");
        }

        // Validate citizenship (0 or 1)
        int citizenship = Integer.parseInt(citizenshipDigit);
        if (citizenship != 0 && citizenship != 1) {
            throw new IllegalArgumentException("Invalid South African ID number: Invalid citizenship digit (" +
                    citizenshipDigit + "). Must be 0 (SA citizen) or 1 (permanent resident).");
        }

        // Validate checksum using Luhn algorithm
        if (!isValidLuhnChecksum(idNumber)) {
            throw new IllegalArgumentException("Invalid South African ID number: Checksum validation failed. " +
                    "ID number may be incorrect.");
        }

        return true;
    }

    /**
     * Gets the maximum days in a month (considering leap years)
     */
    private static int getMaxDaysInMonth(int month, int year) {
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Check for leap year (February)
        if (month == 2) {
            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            return isLeapYear ? 29 : 28;
        }

        return daysInMonth[month - 1];
    }

    /**
     * Validates the checksum using Luhn algorithm (also known as the "mod 10" algorithm)
     */
    private static boolean isValidLuhnChecksum(String idNumber) {
        int sum = 0;
        boolean alternate = false;

        // Process from rightmost digit
        for (int i = idNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(idNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = digit - 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    /**
     * Validates passport number format
     */
    public static boolean isValidPassportNumber(String passportNumber) {
        requireNotEmptyOrNull(passportNumber, "Passport Number");

        // Basic passport validation - alphanumeric, 6-9 characters
        String passportRegex = "^[A-Z0-9]{6,9}$";
        Pattern pattern = Pattern.compile(passportRegex);

        if (!pattern.matcher(passportNumber.toUpperCase()).matches()) {
            throw new IllegalArgumentException("Invalid passport number format: " + passportNumber +
                    ". Must be 6-9 alphanumeric characters.");
        }
        return true;
    }

    // ==================== DATE VALIDATIONS ====================

    /**
     * Validates that start date is before end date
     */
    public static void validateDateRange(LocalDateTime start, LocalDateTime end, String startField, String endField) {
        requireNonNull(start, startField);
        requireNonNull(end, endField);

        if (start.isAfter(end)) {
            throw new IllegalArgumentException(startField + " must be before " + endField +
                    ". Start: " + start + ", End: " + end);
        }
    }

    /**
     * Validates that a date is in the future
     */
    public static void validateFutureDate(LocalDateTime date, String fieldName) {
        requireNonNull(date, fieldName);

        if (date.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(fieldName + " must be in the future. Provided: " + date);
        }
    }

    /**
     * Validates that a date is in the past
     */
    public static void validatePastDate(LocalDateTime date, String fieldName) {
        requireNonNull(date, fieldName);

        if (date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(fieldName + " must be in the past. Provided: " + date);
        }
    }

    // ==================== RANGE VALIDATIONS ====================

    /**
     * Validates that a value is within a specified range
     */
    public static void validateRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(fieldName + " must be between " + min + " and " + max +
                    ". Provided: " + value);
        }
    }

    /**
     * Validates that a double value is within a specified range
     */
    public static void validateRange(double value, double min, double max, String fieldName) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(fieldName + " must be between " + min + " and " + max +
                    ". Provided: " + value);
        }
    }

    // ==================== BOOLEAN VALIDATIONS ====================

    /**
     * Validates that a condition is true
     */
    public static void requireTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a condition is false
     */
    public static void requireFalse(boolean condition, String message) {
        if (condition) {
            throw new IllegalArgumentException(message);
        }
    }

    // ==================== PRICE/AMOUNT VALIDATIONS ====================

    /**
     * Validates that an amount is not negative
     */
    public static void requireValidAmount(double amount, String fieldName) {
        if (amount < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative. Amount: " + amount);
        }
    }

    /**
     * Validates that a discount percentage is valid (0-100)
     */
    public static void requireValidDiscount(double discount, String fieldName) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException(fieldName + " must be between 0 and 100. Provided: " + discount);
        }
    }

    // ==================== CREDIT CARD VALIDATIONS ====================

    /**
     * Validates credit card number using Luhn algorithm
     */
    public static boolean isValidCreditCardNumber(String cardNumber) {
        requireNotEmptyOrNull(cardNumber, "Card Number");

        // Remove any spaces or dashes
        cardNumber = cardNumber.replaceAll("[\\s-]", "");

        // Check if all digits
        if (!cardNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid credit card number: Must contain only digits. Provided: " + cardNumber);
        }

        // Check length (13-19 digits)
        if (cardNumber.length() < 13 || cardNumber.length() > 19) {
            throw new IllegalArgumentException("Invalid credit card number length: " + cardNumber.length() +
                    ". Must be between 13 and 19 digits.");
        }

        // Luhn algorithm
        return isValidLuhnChecksum(cardNumber);
    }

    /**
     * Validates expiry date format (MM/YY)
     */
    public static boolean isValidExpiryDate(String expiryDate) {
        requireNotEmptyOrNull(expiryDate, "Expiry Date");

        // Check format MM/YY
        if (!expiryDate.matches("^(0[1-9]|1[0-2])/[0-9]{2}$")) {
            throw new IllegalArgumentException("Invalid expiry date format: " + expiryDate +
                    ". Expected format: MM/YY");
        }

        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear() % 100;
        int currentMonth = now.getMonthValue();

        // Check if card is expired
        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            throw new IllegalArgumentException("Credit card has expired: " + expiryDate);
        }

        return true;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Truncates a string to specified length
     */
    public static String truncateString(String value, int maxLength) {
        if (value == null) return null;
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }

    /**
     * Checks if a string contains only letters and spaces
     */
    public static boolean isAlphaWithSpaces(String value) {
        if (value == null) return false;
        return value.matches("^[A-Za-z\\s]+$");
    }

    /**
     * Validates postal code format (South Africa: 4 digits)
     */
    public static boolean isValidPostalCode(String postalCode) {
        requireNotEmptyOrNull(postalCode, "Postal Code");

        if (!postalCode.matches("\\d{4}")) {
            throw new IllegalArgumentException("Invalid South African postal code: " + postalCode +
                    ". Must be exactly 4 digits.");
        }
        return true;
    }

    /**
     * Validates age (must be >= 0)
     */
    public static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative. Provided: " + age);
        }
        if (age > 120) {
            throw new IllegalArgumentException("Invalid age: " + age + ". Age must be between 0 and 120.");
        }
    }

    /**
     * Validates star rating (1-5)
     */
    public static void validateStarRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Star rating must be between 1 and 5. Provided: " + rating);
        }
    }

    /**
     * Validates room number format
     */
    public static boolean isValidRoomNumber(String roomNumber) {
        requireNotEmptyOrNull(roomNumber, "Room Number");

        if (!roomNumber.matches("^[A-Z0-9]{2,5}$")) {
            throw new IllegalArgumentException("Invalid room number format: " + roomNumber +
                    ". Must be 2-5 alphanumeric characters.");
        }
        return true;
    }
}









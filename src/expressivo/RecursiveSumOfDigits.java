package expressivo;

public class RecursiveSumOfDigits {

    /**
     * Computes the sum of the digits of a given integer.
     * @param number the input number (can be positive or negative)
     * @return the sum of the digits of the absolute value of the number
     * @throws IllegalArgumentException if the number is Integer.MIN_VALUE
     */
    public static int sumOfDigits(int number) {
        // Handle Integer.MIN_VALUE explicitly
        if (number == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Integer.MIN_VALUE is not supported due to overflow.");
        }

        // Convert negative numbers to positive
        number = Math.abs(number);

        // Base case: when the number is 0
        if (number == 0) {
            return 0;
        }

        // Debugging: Print the current number and the digit being added
        int lastDigit = number % 10; // Get the last digit
        int remainingNumber = number / 10; // Remove the last digit

        // Recursive step: isolate the addition of digits
        return lastDigit + sumOfDigits(remainingNumber);
    }
}

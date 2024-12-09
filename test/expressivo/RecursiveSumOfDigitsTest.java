package expressivo;

import org.junit.Test;
import static org.junit.Assert.*;

public class RecursiveSumOfDigitsTest {

    @Test
    public void testPositiveNumbers() {
        assertEquals(6, RecursiveSumOfDigits.sumOfDigits(123));
        assertEquals(15, RecursiveSumOfDigits.sumOfDigits(555));
        assertEquals(1, RecursiveSumOfDigits.sumOfDigits(1000));
    }

    @Test
    public void testNegativeNumbers() {
        assertEquals(6, RecursiveSumOfDigits.sumOfDigits(-123));
        assertEquals(15, RecursiveSumOfDigits.sumOfDigits(-555));
        assertEquals(1, RecursiveSumOfDigits.sumOfDigits(-1000));
    }

    @Test
    public void testZero() {
        assertEquals(0, RecursiveSumOfDigits.sumOfDigits(0));
    }

    @Test
    public void testLargeNumbers() {
        int result1 = RecursiveSumOfDigits.sumOfDigits(987654321); // New test number
        int result2 = RecursiveSumOfDigits.sumOfDigits(888888);    // New test number

        System.out.println("Sum for 987654321: " + result1);
        System.out.println("Sum for 888888: " + result2);

        assertEquals("Sum of digits for 987654321 should be 45", 45, result1);
        assertEquals("Sum of digits for 888888 should be 48", 48, result2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNumber() {
        RecursiveSumOfDigits.sumOfDigits(Integer.MIN_VALUE);
    }
}

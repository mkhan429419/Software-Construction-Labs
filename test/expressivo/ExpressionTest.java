package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExpressionTest {

    // Test cases for Number variant
    @Test
    public void testNumberEquality() {
        Expression num = new Number(3.5);
        assertEquals("3.5", num.toString());
        assertEquals(new Number(3.5), num); // Structural equality
    }

    @Test
    public void testNumberInequality() {
        Expression num = new Number(3.5);
        assertNotEquals(new Number(4.0), num); // Different numbers
        assertEquals("3.5", num.toString());  // String representation
    }

    @Test
    public void testNumberPrecisionEquality() {
        Expression num1 = new Number(1.000);
        Expression num2 = new Number(1.0);
        assertEquals(num1, num2); // 1.0 == 1.000
        assertEquals(num1.hashCode(), num2.hashCode());
    }

    @Test
    public void testNumberToStringPrecision() {
        Expression num = new Number(1.000);
        assertEquals("1.0", num.toString()); // Ensure consistent string representation
    }

    // Test cases for Variable variant
    @Test
    public void testVariableEquality() {
        Expression var = new Variable("z");
        assertEquals("z", var.toString());
        assertEquals(new Variable("z"), var); // Structural equality
    }

    @Test
    public void testVariableInequality() {
        Expression var = new Variable("x");
        assertNotEquals(new Variable("y"), var); // Different variables
        assertEquals("x", var.toString());       // String representation
    }

    @Test
    public void testVariableCaseSensitivity() {
        Expression var1 = new Variable("x");
        Expression var2 = new Variable("X");
        assertNotEquals(var1, var2); // Case-sensitive comparison
    }

    @Test
    public void testVariableSpecialCharacters() {
        Expression var = new Variable("var_123");
        assertEquals("var_123", var.toString());
        assertEquals(new Variable("var_123"), var);
    }

    // Test cases for Addition variant
    @Test
    public void testAdditionEquality() {
        Expression addition = new Addition(new Number(2), new Variable("a"));
        assertEquals("(2.0 + a)", addition.toString());
        assertEquals(new Addition(new Number(2), new Variable("a")), addition); // Structural equality
    }

    @Test
    public void testAdditionInequality() {
        Expression addition = new Addition(new Number(3), new Variable("b"));
        assertNotEquals(new Addition(new Variable("b"), new Number(3)), addition); // Order matters
        assertEquals("(3.0 + b)", addition.toString());                            // String representation
    }

    @Test
    public void testAdditionGrouping() {
        Expression expr1 = new Addition(new Addition(new Number(1), new Number(2)), new Number(3));
        Expression expr2 = new Addition(new Number(1), new Addition(new Number(2), new Number(3)));
        assertNotEquals(expr1, expr2); // Different grouping
    }

    @Test
    public void testAdditionToString() {
        Expression addition = new Addition(new Number(3), new Addition(new Variable("x"), new Number(4)));
        assertEquals("(3.0 + (x + 4.0))", addition.toString()); // Ensure nested grouping is represented
    }

    // Test cases for Multiplication variant
    @Test
    public void testMultiplicationEquality() {
        Expression multiplication = new Multiplication(new Variable("p"), new Number(5));
        assertEquals("(p * 5.0)", multiplication.toString());
        assertEquals(new Multiplication(new Variable("p"), new Number(5)), multiplication); // Structural equality
    }

    @Test
    public void testMultiplicationInequality() {
        Expression multiplication = new Multiplication(new Variable("q"), new Number(6));
        assertNotEquals(new Multiplication(new Number(6), new Variable("q")), multiplication); // Order matters
        assertEquals("(q * 6.0)", multiplication.toString());                                  // String representation
    }

    @Test
    public void testMultiplicationGrouping() {
        Expression expr1 = new Multiplication(new Multiplication(new Number(2), new Number(3)), new Number(4));
        Expression expr2 = new Multiplication(new Number(2), new Multiplication(new Number(3), new Number(4)));
        assertNotEquals(expr1, expr2); // Different grouping
    }

    @Test
    public void testMultiplicationToString() {
        Expression multiplication = new Multiplication(new Number(3), new Multiplication(new Variable("y"), new Number(4)));
        assertEquals("(3.0 * (y * 4.0))", multiplication.toString()); // Ensure nested grouping is represented
    }

    // Combined tests for Addition and Multiplication
    @Test
    public void testCombinedExpressionsEquality() {
        Expression expr1 = new Addition(
            new Multiplication(new Number(2), new Variable("x")),
            new Variable("y")
        );
        Expression expr2 = new Addition(
            new Multiplication(new Number(2), new Variable("x")),
            new Variable("y")
        );
        assertEquals(expr1, expr2); // Structural equality
        assertEquals(expr1.hashCode(), expr2.hashCode()); // Consistent hash code
    }

    @Test
    public void testCombinedExpressionsInequality() {
        Expression expr1 = new Addition(
            new Multiplication(new Number(2), new Variable("x")),
            new Variable("y")
        );
        Expression expr2 = new Addition(
            new Variable("y"),
            new Multiplication(new Number(2), new Variable("x"))
        );
        assertNotEquals(expr1, expr2); // Order matters
    }
}

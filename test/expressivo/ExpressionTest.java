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
}

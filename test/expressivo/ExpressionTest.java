package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExpressionTest {

//	@Test
//	public void testValidExpressions() {
//	    Expression expr1 = Expression.parse("3 + 2.4");
//	    System.out.println("Parsed Expression for '3 + 2.4': " + expr1.toString());
//	    assertEquals("(3.0 + 2.4)", expr1.toString());
//
//	}
//
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testInvalidExpression() {
//	    Expression.parse("3 *");
//	}
    
    @Test
    public void testDifferentiate() {
        Expression expr = new Addition(new Variable("x"), new Number(5));
        Expression derivative = expr.differentiate("x");
        assertEquals("(1 + 0)", derivative.toString());
    }
    
    @Test
    public void testDifferentiateConstant() {
        Expression constant = new Number(42);
        assertEquals("0", constant.differentiate("x").toString());
    }
    @Test
    public void testDifferentiateVariable() {
        Expression variable = new Variable("x");
        assertEquals("1", variable.differentiate("x").toString());

        Expression otherVariable = new Variable("y");
        assertEquals("0", otherVariable.differentiate("x").toString());
    }
    @Test
    public void testDifferentiateAddition() {
        Expression addition = new Addition(new Variable("x"), new Number(5));
        assertEquals("(1 + 0)", addition.differentiate("x").toString());
    }
    @Test
    public void testDifferentiateMultiplication() {
        Expression multiplication = new Multiplication(new Variable("x"), new Number(5));
        assertEquals("((1 * 5) + (x * 0))", multiplication.differentiate("x").toString());

        Expression complexMultiplication = new Multiplication(new Variable("x"), new Variable("y"));
        assertEquals("((1 * y) + (x * 0))", complexMultiplication.differentiate("x").toString());
    }

}

/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;
import java.io.IOException;
/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    //   TODO
	 /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
	public static Expression parse(String input) {
	    input = input.replaceAll("\\s+", ""); // Remove whitespace for simplicity
	    return parseExpression(new Tokenizer(input));
	}

	private static Expression parseExpression(Tokenizer tokenizer) {
	    Expression left = parseTerm(tokenizer);

	    while (tokenizer.hasNext() && tokenizer.peek().equals("+")) {
	        tokenizer.consume("+");
	        Expression right = parseTerm(tokenizer);
	        left = new Addition(left, right);
	    }

	    return left;
	}

	private static Expression parseTerm(Tokenizer tokenizer) {
	    Expression left = parseFactor(tokenizer);

	    while (tokenizer.hasNext() && tokenizer.peek().equals("*")) {
	        tokenizer.consume("*");
	        Expression right = parseFactor(tokenizer);
	        left = new Multiplication(left, right);
	    }

	    return left;
	}

	private static Expression parseFactor(Tokenizer tokenizer) {
	    if (!tokenizer.hasNext()) {
	        throw new IllegalArgumentException("Unexpected end of input");
	    }

	    String token = tokenizer.peek();

	    if (token.matches("\\d+(\\.\\d+)?")) { // Matches a number
	        tokenizer.consume();
	        return new Number(Double.parseDouble(token));
	    }

	    if (token.matches("[a-zA-Z]+")) { // Matches a variable
	        tokenizer.consume();
	        return new Variable(token);
	    }

	    if (token.equals("(")) { // Matches parentheses
	        tokenizer.consume(); // Consume '('
	        Expression expr = parseExpression(tokenizer); // Parse the inner expression
	        if (!tokenizer.hasNext() || !tokenizer.consume().equals(")")) {
	            throw new IllegalArgumentException("Mismatched parentheses");
	        }
	        return expr;
	    }

	    throw new IllegalArgumentException("Unexpected token: " + token);
	}


   
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    Expression differentiate(String variable);
    
    // TODO more instance methods
    
}